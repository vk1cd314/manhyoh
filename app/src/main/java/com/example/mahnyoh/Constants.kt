package com.example.mahnyoh

class Constants {

    /**
     * Constants class : This class contains the information and properties of
     * each exercises.
     *
     * It has a companion object that is used to return the  list of
     * Exercises in the form of ArrayList ie ArrayList<ExerciseModel>.
     *
     */


    //companion object concept is similar to static variables in java
    companion object{
        fun defaultExerciseList() : ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val highknees = ExerciseModel(
                1,
                "High Knees",
                R.raw.highknees,
                false,
                false
            )
            exerciseList.add(highknees)


            val Burpees = ExerciseModel(
                2,
                "Burpee",
                R.raw.burpee ,
                false,
                false
            )
            exerciseList.add(Burpees)


            val  mountainclimbers= ExerciseModel(
                3,
                "Mountain Climbers",
                R.raw.mountainclimbing ,
                false,
                false
            )
            exerciseList.add(mountainclimbers)

            val  squatjump= ExerciseModel(
                4,
                "Squat Jump",
                R.raw.squatjump ,
                false,
                false
            )
            exerciseList.add(squatjump)

            val  buttkickers = ExerciseModel(
                5,
                "Butt Kickers",
                R.raw.buttkicker ,
                false,
                false
            )
            exerciseList.add(buttkickers)

            val  armroll= ExerciseModel(
                6,
                "Forward Arm Roll",
                R.raw.armroll ,
                false,
                false
            )
            exerciseList.add(armroll)

            val  pushup = ExerciseModel(
                7,
                "Kneeled Wide Pushup",
                R.raw.kneelpushup,
                false,
                false
            )
            exerciseList.add(pushup)

            val  plank = ExerciseModel(
                8,
                "Plank Shoulder Tap",
                R.raw.shouldertaps ,
                false,
                false
            )
            exerciseList.add(plank)


            val  jumpingjack = ExerciseModel(
                9,
                "Jumping Jack",
                R.raw.jumpingjack ,
                false,
                false
            )
            exerciseList.add(jumpingjack)


            return exerciseList
        }
    }

}