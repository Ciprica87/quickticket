package com.ciprian.ticketmanagementsystem.ui.screens

import android.Manifest
import android.content.res.Resources
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ciprian.ticketmanagementsystem.R
import com.ciprian.ticketmanagementsystem.ui.theme.RoyalBlue
import com.ciprian.ticketmanagementsystem.ui.theme.TicketManagementSystemTheme
import com.ciprian.ticketmanagementsystem.ui.viewmodels.ScanState
import com.ciprian.ticketmanagementsystem.ui.viewmodels.TicketCheckInViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.PlanarYUVLuminanceSource
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    username: String,
    ticketCheckInViewModel: TicketCheckInViewModel,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var scanState by remember { mutableStateOf<ScanState>(ScanState.Scanning) }
    var indicatorText by remember { mutableStateOf("Indicator") }
    var indicatorColor by remember { mutableStateOf(Color.Gray) }
    var scannedTicketTier by remember { mutableStateOf<String?>(null) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val zxingReader = remember { MultiFormatReader() }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                startCamera(
                    previewView,
                    cameraProviderFuture,
                    zxingReader,
                    scanState,
                    { newState -> scanState = newState },
                    ticketCheckInViewModel
                )
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    TicketManagementSystemTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_bg),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = username, color = Color.White)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = "Logout", color = Color.White)
                                IconButton(onClick = { onLogout() }) {
                                    Icon(Icons.Default.ExitToApp, contentDescription = stringResource(id = R.string.logout), tint = Color.White)
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = RoyalBlue)
                )

                Spacer(modifier = Modifier.height(120.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(
                        factory = { context ->
                            previewView.apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    300.dp.toPx().toInt(),
                                    300.dp.toPx().toInt()
                                )
                            }
                        },
                        modifier = Modifier
                            .size(300.dp)
                            .background(Color.Transparent)
                    )
                }

                Spacer(modifier = Modifier.height(76.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = indicatorColor)
                    ) {
                        Text(text = indicatorText)
                    }

                    scannedTicketTier?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = "Ticket Tier: $it",
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            scanState = ScanState.Scanning
                            indicatorColor = Color.Gray
                            indicatorText = "Indicator"
                            scannedTicketTier = null
                            ticketCheckInViewModel.resetState()

                            startCamera(
                                previewView,
                                cameraProviderFuture,
                                zxingReader,
                                scanState,
                                { newState -> scanState = newState },
                                ticketCheckInViewModel
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(48.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                    ) {
                        Text(text = stringResource(id = R.string.scan_next))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    LaunchedEffect(ticketCheckInViewModel.checkInResult) {
        ticketCheckInViewModel.checkInResult.collect { result ->
            result?.onSuccess { ticketCheckInDTO ->
                if (ticketCheckInDTO.isValid) {
                    indicatorColor = Color.Green
                    indicatorText = "Valid"
                    scannedTicketTier = ticketCheckInDTO.ticketTierName
                } else {
                    indicatorColor = Color.Red
                    indicatorText = "Invalid"
                    scannedTicketTier = ""
                }
                scanState = ScanState.Stopped
            }?.onFailure { exception ->
                indicatorColor = Color.Red
                indicatorText = "Invalid"
                scannedTicketTier = ""
                Toast.makeText(context, exception.message ?: "Unknown error occurred", Toast.LENGTH_SHORT).show()
                scanState = ScanState.Stopped
            }
        }
    }
}


private fun startCamera(
    previewView: PreviewView,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    zxingReader: MultiFormatReader,
    scanState: ScanState,
    updateScanState: (ScanState) -> Unit,
    ticketCheckInViewModel: TicketCheckInViewModel
) {
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val analyzer = ImageAnalysis.Analyzer { imageProxy ->
            if (scanState == ScanState.Scanning) {
                try {
                    val buffer = imageProxy.planes[0].buffer
                    val bytes = buffer.toByteArray()
                    val source = PlanarYUVLuminanceSource(
                        bytes, imageProxy.width, imageProxy.height, 0, 0,
                        imageProxy.width, imageProxy.height, false
                    )
                    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
                    val result = zxingReader.decode(binaryBitmap)

                    val ticketId = result.text
                    Log.d("ScanScreen", "Scanned Ticket ID: $ticketId")

                    updateScanState(ScanState.Stopped)

                    cameraProvider.unbind(imageAnalysis)

                    ticketCheckInViewModel.validateTicket(ticketId)

                } catch (e: Exception) {
                    Log.e("ScanScreen", "Error decoding barcode", e)
                    updateScanState(ScanState.Stopped)

                    cameraProvider.unbind(imageAnalysis)
                } finally {
                    imageProxy.close()
                }
            } else {
                imageProxy.close()
            }
        }

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(previewView.context), analyzer)

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                previewView.context as ComponentActivity, cameraSelector, preview, imageAnalysis
            )
        } catch (exc: Exception) {
            Toast.makeText(previewView.context, "Failed to start camera: ${exc.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }, ContextCompat.getMainExecutor(previewView.context))
}


fun ByteBuffer.toByteArray(): ByteArray {
    rewind()
    val data = ByteArray(remaining())
    get(data)
    return data
}

private fun Dp.toPx() = this.value * Resources.getSystem().displayMetrics.density
