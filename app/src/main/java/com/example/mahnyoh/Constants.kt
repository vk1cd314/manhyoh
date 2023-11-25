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
    companion object {
        fun CardioExerciseList(): ArrayList<ExerciseModel> {
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
                R.raw.burpee,
                false,
                false
            )
            exerciseList.add(Burpees)


            val mountainclimbers = ExerciseModel(
                3,
                "Mountain Climbers",
                R.raw.mountainclimbing,
                false,
                false
            )
            exerciseList.add(mountainclimbers)

            val squatjump = ExerciseModel(
                4,
                "Squat Jump",
                R.raw.squatjump,
                false,
                false
            )
            exerciseList.add(squatjump)

            val buttkickers = ExerciseModel(
                5,
                "Butt Kickers",
                R.raw.buttkicker,
                false,
                false
            )
            exerciseList.add(buttkickers)

            val armroll = ExerciseModel(
                6,
                "Forward Arm Roll",
                R.raw.armroll,
                false,
                false
            )
            exerciseList.add(armroll)

            val pushup = ExerciseModel(
                7,
                "Kneeled Wide Pushup",
                R.raw.kneelpushup,
                false,
                false
            )
            exerciseList.add(pushup)

            val plank = ExerciseModel(
                8,
                "Plank Shoulder Tap",
                R.raw.shouldertaps,
                false,
                false
            )
            exerciseList.add(plank)


            val jumpingjack = ExerciseModel(
                9,
                "Jumping Jack",
                R.raw.jumpingjack,
                false,
                false
            )
            exerciseList.add(jumpingjack)


            return exerciseList
        }


    fun AgilityExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingjackS = ExerciseModel(
            1,
            "Jumping Jack",
            R.raw.jumpingjack,
            false,
            false
        )
        exerciseList.add(jumpingjackS)


        val highknees = ExerciseModel(
            2,
            "High Knees",
            R.raw.highknees,
            false,
            false
        )
        exerciseList.add(highknees)


        val pushups = ExerciseModel(
            3,
            "Pushups",
            R.raw.pushup,
            false,
            false
        )
        exerciseList.add(pushups)

        val burpee = ExerciseModel(
            4,
            "Burpee",
            R.raw.burpee,
            false,
            false
        )
        exerciseList.add(burpee)

        val mountain = ExerciseModel(
            5,
            "Mountain Climbers",
            R.raw.mountainclimbing,
            false,
            false
        )
        exerciseList.add(mountain)

        val crunch = ExerciseModel(
            6,
            "Crunches",
            R.raw.crunches,
            false,
            false
        )
        exerciseList.add(crunch)

        val altleg = ExerciseModel(
            7,
            "Alternating Leg Raise",
            R.raw.alternatingleg,
            false,
            false
        )
        exerciseList.add(altleg)

        val twist = ExerciseModel(
            8,
            "Russian Twist",
            R.raw.twist,
            false,
            false
        )
        exerciseList.add(twist)


        val reverse = ExerciseModel(
            9,
            "Reverse Lunge",
            R.raw.reverselung,
            false,
            false
        )
        exerciseList.add(reverse)


        return exerciseList
    }

    fun StrentghExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val planks = ExerciseModel(
            1,
            "Plank",
            R.raw.plank,
            false,
            false
        )
        exerciseList.add(planks)


        val pushups = ExerciseModel(
            2,
            "Pushup",
            R.raw.pushup,
            false,
            false
        )
        exerciseList.add(pushups)


        val inch = ExerciseModel(
            3,
            "Inch Worm",
            R.raw.inchworm,
            false,
            false
        )
        exerciseList.add(inch)

        val lunges = ExerciseModel(
            4,
            "Lunges",
            R.raw.lunges,
            false,
            false
        )
        exerciseList.add(lunges)

        val squat = ExerciseModel(
            5,
            "Squat",
            R.raw.squat,
            false,
            false
        )
        exerciseList.add(squat)

        val twist = ExerciseModel(
            6,
            "Russian Twist",
            R.raw.twist,
            false,
            false
        )
        exerciseList.add(twist)

        val raise = ExerciseModel(
            7,
            "Leg Raise",
            R.raw.legraise,
            false,
            false
        )
        exerciseList.add(raise)

        val flutter = ExerciseModel(
            8,
            "Flutter Kick",
            R.raw.flutterkick,
            false,
            false
        )
        exerciseList.add(flutter)


        val crunches = ExerciseModel(
            9,
            "Crunches",
            R.raw.crunches,
            false,
            false
        )
        exerciseList.add(crunches)


        return exerciseList
    }

    fun BalanceExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val single = ExerciseModel(
            1,
            "Single Leg Raise",
            R.raw.singleleg,
            false,
            false
        )
        exerciseList.add(single)


        val squat = ExerciseModel(
            2,
            "Squat",
            R.raw.burpee,
            false,
            false
        )
        exerciseList.add(squat)


        val shoulder = ExerciseModel(
            3,
            "Shoulder Tap",
            R.raw.shouldertaps,
            false,
            false
        )
        exerciseList.add(shoulder)

        val both = ExerciseModel(
            4,
            "Both Leg Raise",
            R.raw.leg,
            false,
            false
        )
        exerciseList.add(both)

        val alternate = ExerciseModel(
            5,
            "Alternating Leg Raise",
            R.raw.alternatingleg,
            false,
            false
        )
        exerciseList.add(alternate)

        val balance = ExerciseModel(
            6,
            "Balancing Stick",
            R.raw.balancingstick,
            false,
            false
        )
        exerciseList.add(balance)

        val side = ExerciseModel(
            7,
            "Side Leg Raise",
            R.raw.sideleg,
            false,
            false
        )
        exerciseList.add(side)

        val reverse = ExerciseModel(
            8,
            "Reverse Lunge",
            R.raw.reverselung,
            false,
            false
        )
        exerciseList.add(reverse)


        val legcrunch = ExerciseModel(
            9,
            "Leg Raise Crunch",
            R.raw.legraisecrunch,
            false,
            false
        )
        exerciseList.add(legcrunch)


        return exerciseList
    }

    fun FlexExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val lunge = ExerciseModel(
            1,
            "Lunges",
            R.raw.lunges,
            false,
            false
        )
        exerciseList.add(lunge)


        val neck = ExerciseModel(
            2,
            "Neck Stretch",
            R.raw.neckstretch,
            false,
            false
        )
        exerciseList.add(neck)


        val glut = ExerciseModel(
            3,
            "Glut Bridge",
            R.raw.glut,
            false,
            false
        )
        exerciseList.add(glut)

        val bowpose = ExerciseModel(
            4,
            "Bow Pose",
            R.raw.bowpose,
            false,
            false
        )
        exerciseList.add(bowpose)

        val seated = ExerciseModel(
            5,
            "Seated Forward Bend",
            R.raw.seatedbend,
            false,
            false
        )
        exerciseList.add(seated)

        val knees = ExerciseModel(
            6,
            "Knees to Chest",
            R.raw.kneetochest,
            false,
            false
        )
        exerciseList.add(knees)

        val cobra = ExerciseModel(
            7,
            "Cobra Stretching",
            R.raw.cobra,
            false,
            false
        )
        exerciseList.add(cobra)

        val sidelunge = ExerciseModel(
            8,
            "Side Lunges",
            R.raw.sidelunges,
            false,
            false
        )
        exerciseList.add(sidelunge)


        val tilt = ExerciseModel(
            9,
            "Side Tilts",
            R.raw.sidetilts,
            false,
            false
        )
        exerciseList.add(tilt)


        return exerciseList
    }

}

}

