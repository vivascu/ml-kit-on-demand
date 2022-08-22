package ch.vivascu.dynamic.downloadable

import android.os.Bundle
import ch.vivascu.dynamic.BaseSplitActivity
import com.google.mlkit.common.internal.CommonComponentRegistrar
import com.google.mlkit.common.sdkinternal.MlKitContext
import com.google.mlkit.vision.common.internal.VisionCommonRegistrar
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.google.mlkit.vision.pose.internal.PoseRegistrar

class DownloadedActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_activity)

        val registrars = listOf(
            CommonComponentRegistrar(),
            VisionCommonRegistrar(),
            PoseRegistrar(),
        )

        MlKitContext.initialize(this, registrars)

        val options = PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()

        val poseDetector = PoseDetection.getClient(options)
    }
}
