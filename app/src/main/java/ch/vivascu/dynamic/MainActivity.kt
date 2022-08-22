package ch.vivascu.dynamic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*
import kotlin.math.roundToInt

class MainActivity : BaseSplitActivity() {
    var textState = "Download Module"
    var mySessionId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val splitInstallManager = SplitInstallManagerFactory.create(this)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val request =
                SplitInstallRequest
                    .newBuilder()
                    .addModule("downloadable")
                    .build()

            splitInstallManager
                .startInstall(request)
                .addOnSuccessListener { sessionId -> mySessionId = sessionId }
                .addOnFailureListener { exception ->
                    exception.toString()
                }
        }

        val intent = Intent()
        intent.setClassName(
            "ch.vivascu.dynamic",
            "ch.vivascu.dynamic.downloadable.DownloadedActivity"
        )

        val listener = SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() == mySessionId) {
                // Read the status of the request to handle the state update.

                textState = when (state.status()) {

                    CANCELED -> "Canceled"
                    CANCELING -> "Canceling"
                    DOWNLOADED -> "Downloaded"
                    DOWNLOADING -> "Downloading"
                    FAILED -> "Failed"
                    INSTALLED -> "Installed"
                    INSTALLING -> "Installing"
                    PENDING -> "Pending"
                    REQUIRES_USER_CONFIRMATION -> "Requires user confirmation"
                    else -> "Unknown Status"
                }


                if (state.status() == DOWNLOADING) {
                    val totalBytes = state.totalBytesToDownload()
                    val progress = state.bytesDownloaded()
                    val percent = progress * 100f / totalBytes
                    textState += " ${(percent * 100).roundToInt() / 100}%"
                    button.text = textState
                }

                if (state.status() == INSTALLED) {
                    startActivity(intent)
                }

            }
        }

        splitInstallManager.registerListener(listener)

    }
}