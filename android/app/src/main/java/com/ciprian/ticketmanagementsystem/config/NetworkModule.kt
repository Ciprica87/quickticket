package com.ciprian.ticketmanagementsystem.config

import android.content.Context
import com.ciprian.ticketmanagementsystem.R
import com.ciprian.ticketmanagementsystem.data.remote.api.AuthInterceptor
import com.ciprian.ticketmanagementsystem.data.remote.api.AuthService
import com.ciprian.ticketmanagementsystem.data.remote.api.TicketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        // Load the certificate from the raw resource folder
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val inputStream: InputStream = context.resources.openRawResource(R.raw.certificate)
        val ca: X509Certificate = inputStream.use {
            certificateFactory.generateCertificate(it) as X509Certificate
        }

        // Create a KeyStore containing our trusted CAs
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs in our KeyStore
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustManagerFactory.trustManagers, null)
        }

        // Define a HostnameVerifier that bypasses hostname verification for development purposes
        val hostnameVerifier = HostnameVerifier { hostname, session ->
            hostname == "192.168.88.137" || hostname == "localhost"
        }

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
            .hostnameVerifier(hostnameVerifier)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://192.168.88.137:8443/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideTicketService(retrofit: Retrofit): TicketService =
        retrofit.create(TicketService::class.java)
}
