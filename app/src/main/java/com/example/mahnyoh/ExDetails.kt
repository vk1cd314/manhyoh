package com.example.mahnyoh

import ExerciseSessionData
import android.app.Dialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.example.mahnyoh.data.HealthConnectManager
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * There are two timers used
 * 1) RestTimer: This timer is for the user to get ready upcoming exercise
 * 2) ExercieTimer: This timer is for the user while performing the exercise
 *
 * ExerciseActivity is extended with TextToSpeech OnInitListener
 */


class ExDetails (exerciseName : String=""): AppCompatActivity(), TextToSpeech.OnInitListener {
    //variable for rest timer
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0// progress counts from 0 to 10

    //TO DO: change the restTimerDuration from 2 to 10 seconds after testing
    private var restTimerDuration: Long = 10

    //variable for exercise timer
    private var exerciseTimeFixed :Long =45
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 0// progress from 0 to 30


    //TO DO: change the exerciseTimerDuration from 2 to 30 seconds after testing
    private var exerciseTimerDuration : Long = 45
    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    //text to speech variable
    private var tts: TextToSpeech? = null

    //adding media player
    private var player: MediaPlayer? = null

    private var exercisename: String? =null

    private var isExercisePaused = false
    private var isRestPaused=false

    private var restView=false
    private var exerciseview=false

    // Inside ExDetails class
    private var remainingRestTime: Long = 10
    private var remainingExerciseTime: Long = 0




    private lateinit var healthConnectManager: HealthConnectManager
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Set<String>>

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.all { it.value }) {
            Log.i("WHAAAAT", "All required permissions granted")
        } else {
            Log.w("WHAAAT", "Not all required permissions granted")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex_details)

        //adding text to speech feature
        tts = TextToSpeech(this,this)

        exercisename=intent.getStringExtra("exerciseName").toString()
        Log.e("Ki pacchi",exercisename.toString())
        if(exercisename=="Cardio")
        {
            exerciseList = Constants.CardioExerciseList()

        }

        else if(exercisename=="Agility")
        {
            exerciseList = Constants.AgilityExerciseList()

        }

        else if(exercisename=="Strength")
        {
            exerciseList = Constants.StrentghExerciseList()

        }

        else if(exercisename=="Balance")
        {
            exerciseList = Constants.BalanceExerciseList()

        }
        else if(exercisename=="Flexibility")
        {
            exerciseList = Constants.FlexExerciseList()

        }

        setupRestView()


      healthConnectManager = HealthConnectManager(this)
        Log.d("WHAAAT", "Checking permissions")
        permissionLauncher.launch(ExDetails.permissions)
       // Log.d("checking",)



        // setupExerciseStatusRecyclerView()
    }

    //onDestroy() is for resetting the timers and stop and shutdown the tts
    override fun onDestroy(){
        //resetting the restTimer
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        //resetting the exerciseTimer
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        //destroying text to speech feature variable tts
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        //destroying media player
        if(player != null){
            player!!.stop()
        }

        restView=false
        exerciseview=false


        //resetting the timer
        super.onDestroy()
    }

    //This fun is for setting the rest timer for the user to get ready for upcoming exercise
    private fun setRestProgressBar(duration: Long){
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer=findViewById<TextView>(R.id.tvTimer)

        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(duration*1000, 1000){
            //onTick() is for countdown interval
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = (millisUntilFinished/1000).toInt()//restTimerDuration.toInt()- restProgress
                tvTimer.text = (millisUntilFinished/1000).toString()//(restTimerDuration.toInt()- restProgress).toString()
            }

            //on finishing of the restTimer progressing the exercise timer
            override fun onFinish() {
                currentExercisePosition++

                //setting the current exercise to true
                exerciseList!![currentExercisePosition].setIsSelected(true)
                // exerciseAdapter!!.notifyDataSetChanged()

                //once the restTimer is over move to next Exercise screen
                setupExerciseView()
            }
        }
        restTimer?.start()


    }


    //  This function is for starting ExerciseTimer after the user is ready to do exercise
    private fun setExerciseProgressBar(duration:Long){
        val progressBarExercise=findViewById<ProgressBar>(R.id.progressBarExercise)
        val tvExerciseTimer=findViewById<TextView>(R.id.tvExerciseTimer)
        progressBarExercise.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(duration*1000, 1000) {
            //onTick() is for the ExerciseTimer progress
            override fun onTick(millisUntilFinished: Long) {
                Log.e("Callingtime", (millisUntilFinished/1000).toString())
                exerciseProgress++
                progressBarExercise.progress = (millisUntilFinished/1000).toInt()//exerciseTimeFixed.toInt() - exerciseProgress
                tvExerciseTimer.text = (millisUntilFinished/1000).toString()//(exerciseTimeFixed.toInt() - exerciseProgress).toString()
            }
            override fun onFinish() {

                val ivImage=findViewById<VideoView>(R.id.ivVideo)
                ivImage.visibility = View.GONE
                remainingExerciseTime=0
                Log.e("Finish time",exerciseTimerDuration.toString())


                if(currentExercisePosition < exerciseList?.size!! - 1){

                    //if all exercise performance is not completed
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    //exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{

                    customDialogForFinish()
                    //  Call this when exercises activity is done and should be closed.
                }
            }


        }
        exerciseTimer?.start()

        //on finishing a current exercise either move to next or to finish screen

    }



    //setting up the Rest Screen for user to get ready
    private fun setupRestView(){
       /* try{
            //added the media player song
            player = MediaPlayer.create(applicationContext,R.raw.background)
            //preventing the media looping
            player!!.isLooping = false //this will only play the song once
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }*/

        restView=true




        val llRestView=findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView =findViewById<LinearLayout>(R.id.llExerciseView)

        val restPause=findViewById<LinearLayout>(R.id.restPause)

        //making the exercise screen invisible nad rest screen visible
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        restPause.setOnClickListener{
            RestPause()
        }


        if(restTimer != null){
            //resetting the rest timer
            restTimer!!.cancel()
            restProgress = 0
        }

        val tvUpcomingExerciseName=findViewById<TextView>(R.id.tvUpcomingExerciseName)
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar(restTimerDuration)
    }

    //setting the exercise screen
    private fun setupExerciseView() {

        exerciseview=true

        //visibility use
        val llRestView = findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        val ivImage = findViewById<VideoView>(R.id.ivVideo)

        val pause=findViewById<LinearLayout>(R.id.pause)
        val play=findViewById<ImageView>(R.id.play)


        //
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        ivImage.visibility=View.VISIBLE



        //

        pause.setOnClickListener{
            onPauseButtonClick()
        }



            if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        //Function is used to speak the text ie current exercise name
        speakOut(exerciseList!![currentExercisePosition].getName())


        setExerciseProgressBar(exerciseTimeFixed)

        val tvExerciseName = findViewById<TextView>(R.id.tvExerciseName)


        //setting the exercise images and exercise name
        val videoUri =
            Uri.parse("android.resource://" + packageName + "/" + exerciseList!![currentExercisePosition].getVideo())
        ivImage.setVideoURI(videoUri)
        ivImage.start()


        ivImage.setOnCompletionListener { mp ->
            // Restart the video
            mp.start()
        }


        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    //   This is TextToSpeech override function
//    Called to signal the completion of the TextToSpeech engine initialization
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            //setting the language to US English
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language specified is not supported")
                Toast.makeText(this,"Language not Supported",Toast.LENGTH_SHORT).show()
            }else{
                Log.e("TTS","Initialization Failed!")
            }
        }
    }

    //Function is used to speak the text ie current exercise name
    private fun speakOut(text: String){
        //ignore this error: It working fine after installing the app
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    /* private fun setupExerciseStatusRecyclerView(){
          val rvExerciseStatus=findViewById<RecyclerView>(R.id.rvExerciseStatus)
         rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
         exerciseAdapter = ExerciseStatusAdapter(this, exerciseList!!)
         rvExerciseStatus.adapter = exerciseAdapter
     }*/

    private fun pauseVideo() {
        val ivImage = findViewById<VideoView>(R.id.ivVideo)
        if (ivImage.isPlaying) {
            ivImage.pause()
        }
    }

    // Helper function to resume video playback
    private fun resumeVideo() {
        val ivImage = findViewById<VideoView>(R.id.ivVideo)
        ivImage.start()
    }
    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)

        // Set the content view for the dialog
        customDialog.setContentView(R.layout.custom_dialog_for_exit)

        // Find the Buttons within the dialog's layout
        val tvYes = customDialog.findViewById<Button>(R.id.tvYes)
        val tvNo = customDialog.findViewById<Button>(R.id.tvNo)

        // Set click listeners for the buttons
        tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        tvNo.setOnClickListener {
            customDialog.dismiss()
            if(restView==true)
            {
                RestPause()
            }

            if(exerciseview==true)
            {
                onPauseButtonClick()
            }
        }

        // Show the dialog
        customDialog.show()
    }


    fun onPauseButtonClick() {
        isExercisePaused = !isExercisePaused
        val time=findViewById<TextView>(R.id.tvExerciseTimer)
        val play=findViewById<ImageView>(R.id.play)

        if (isExercisePaused) {
            // If exercise is paused, store the remaining time and cancel the timer
            time.visibility=View.GONE
            play.visibility=View.VISIBLE
            remainingExerciseTime=exerciseTimeFixed-exerciseProgress
            exerciseTimer?.cancel()
            pauseVideo()
        } else {
            // If exercise is resumed, start the timer with remaining time
            //    exerciseTimerDuration=remainingExerciseTime

            time.visibility=View.VISIBLE
            play.visibility=View.GONE
            setExerciseProgressBar(remainingExerciseTime)
            resumeVideo()

        }
    }


    fun RestPause() {
        isRestPaused = !isRestPaused
        val time=findViewById<TextView>(R.id.tvTimer)
        val play=findViewById<ImageView>(R.id.playRest)

        if (isRestPaused) {
            // If exercise is paused, store the remaining time and cancel the timer
            time.visibility=View.GONE
            play.visibility=View.VISIBLE
            remainingRestTime=restTimerDuration-restProgress
            restTimer?.cancel()
            pauseVideo()
        } else {
            // If exercise is resumed, start the timer with remaining time
            //    exerciseTimerDuration=remainingExerciseTime
            time.visibility=View.VISIBLE
            play.visibility=View.GONE
            setRestProgressBar(remainingRestTime)
            resumeVideo()

        }
    }
    private fun customDialogForFinish() {
        val customDialog = Dialog(this)

        // Set the content view for the dialog to a full-screen layout
        customDialog.setContentView(R.layout.fragment_dialog)

        // Find the Buttons within the dialog's layout
        val saveButton = customDialog.findViewById<Button>(R.id.saveButton)
        val cancelButton = customDialog.findViewById<Button>(R.id.cancelButton)


        // Set click listeners for the buttons
        saveButton.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        // Show the dialog
        customDialog.show()

//        val endTime = Instant.now()
//        val startTime = endTime.minus(10, ChronoUnit.MINUTES)
       lifecycleScope.launch {
            try {
                Log.i("Updating", "wot")
//                val aggregatedData = healthConnectManager.aggregateHealthData(startTime, endTime)
                var minSpeed = customDialog.findViewById<TextView>(R.id.minspeed)
                var maxSpeed = customDialog.findViewById<TextView>(R.id.maxspeed)
                var avgSpeed = customDialog.findViewById<TextView>(R.id.avgspeed)
                var steps = customDialog.findViewById<TextView>(R.id.stepsDialog)
                var distance = customDialog.findViewById<TextView>(R.id.distanceDialog)

                val uids = healthConnectManager.extractSessionUIDs(Instant.now().minus(30, ChronoUnit.DAYS), Instant.now())
                if (uids.isNotEmpty()) {
                    val mostRecentUID = uids.first() // Assuming the list is ordered by date
                    val aggregatedData = healthConnectManager.readSpecificSessionData(mostRecentUID)
                    // Use the session data as needed
                    minSpeed.text = aggregatedData.minSpeed.toString()
                    maxSpeed.text = aggregatedData.maxSpeed.toString()
                    avgSpeed.text = aggregatedData.avgSpeed.toString()
                    steps.text = aggregatedData.totalSteps.toString()
                    distance.text = aggregatedData.totalDistance.toString()
                } else Log.i("EMPTY", "LIKE MY LIFE")
            } catch (e: Exception) {
                Log.e("HealthData", "Error fetching health data: ${e.message}")
            }
        }

        // Set dialog width and height to match_parent after it's shown
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        customDialog.window?.setLayout(width, height)
    }

   companion object {
        private val permissions = arrayOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getReadPermission(DistanceRecord::class),
            HealthPermission.getReadPermission(SpeedRecord::class),
            HealthPermission.getReadPermission(ExerciseSessionRecord::class)
        )
    }
//    SpeedRecord::class,
//    DistanceRecord::class,
//    StepsRecord::class,
}