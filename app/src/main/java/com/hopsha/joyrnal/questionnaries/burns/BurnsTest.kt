package com.hopsha.joyrnal.questionnaries.burns

import com.hopsha.joyrnal.questionnaries.Test

object BurnsTest: Test<BurnsTest.Question, BurnsTest.Answer, BurnsTest.Result> {

    private val answerSpec = Test.AnswerSpec.Options(
        Answer::class,
        Answer.values().toList()
    )

    enum class Question(
        val answerSpec: Test.AnswerSpec<Answer>
    ): Test.Question {
        // Вам грустно или вы в плохом настроении?
        SAD_OR_BAD_MOOD(answerSpec),

        // Чувствуете грусть, удручены?
        SAD_OR_DEJECTED(answerSpec),

        // Чувствуете желание расплакаться, слезливость?
        WANT_CRY(answerSpec),

        // Чувствуете уныние?
        GLOOM(answerSpec),

        // Испытываете чувство безнадежности?
        HOPELESSNESS(answerSpec),

        // Имеете низкую самооценку?
        LOW_SELF_ESTEEM(answerSpec),

        // Испытываете чувство собственной ничтожности и непригодности?
        NULLITY_AND_WORTHLESSNESS(answerSpec),

        // Испытываете чувство вины или стыда?
        GUILTY_OR_SHAME(answerSpec),

        // Критикуете или обвиняете себя?
        CRITIC_BLAME_YOURSELF(answerSpec),

        // Испытываете трудности с принятием себя?
        HARD_MAKING_DECISIONS(answerSpec),

        // Чувствуете потерю интереса к членам семьи, друзьям, коллегам?
        LOST_INTEREST_FOR_FAMILY_FRIENDS_COLLEAGUES(answerSpec),

        // Испытываете одиночество?
        LONELY(answerSpec),

        // Проводите меньше времени с семьей или с друзьями?
        SPEND_LESS_TIME_WITH_FAMILY_FRIENDS(answerSpec),

        // Чувствуете потерю мотивации?
        LOSE_MOTIVATION(answerSpec),

        // Чувствуете потерю интереса к работе или другим занятиям?
        LOSING_INTEREST_FOR_WORK_BUSINESS(answerSpec),

        // Избегаете работы к другой деятельности?
        AVOID_WORK_BUSINESS(answerSpec),

        // Ощущаете потерю удовольствия и нехватку удовлетворения в жизни?
        LOSING_JOY_MISSING_SATISFACTION_FROM_LIFE(answerSpec),

        // Испытываете усталость?
        TIRED(answerSpec),

        // Испытываете затруднения со сном или, наоборот, слишком много спите?
        DIFFICULTIES_WITH_SLEEP(answerSpec),

        // Имеете сниженный или, наоборот, повышенный аппетит?
        LOW_OR_HIGH_APPETITE(answerSpec),

        // Замечаете потерю интереса к сексу?
        LOST_INTEREST_FOR_SEX(answerSpec),

        // Беспокоитесь по поводу своего здоровья?
        WORRIED_ABOUT_HEALTH(answerSpec),

        // Имеются ли у вас суицидальные мысли?
        SUICIDAL_THOUGHTS(answerSpec),

        // Хотели бы вы окончить свою жизнь?
        WANT_FINISH_LIFE(answerSpec),

        // Планируете ли вы навредить себе?
        PLAN_TO_HURT_YOURSELF(answerSpec)
    }

    enum class Answer(
        val score: Int
    ): Test.Answer {
        NEVER(0),
        SOMETIMES(1),
        MODERATE(2),
        OFTEN(3),
        VERY_OFTEN(4)
    }

    data class Result(
        val score: Int,
        val category: ResultCategory
    ): Test.Result

    enum class ResultCategory(
        val scoreRange: IntRange
    ) {
        NO_DEPRESSION(0..5),
        NORMAL_BUT_UNHAPPY(6..10),
        FAINT_DEPRESSION(11..25),
        MODERATE_DEPRESSION(26..50),
        STRONG_DEPRESSION(51..75),
        EXTREME_DEPRESSION(76..100)
    }

    override val questions: List<Pair<Question, Test.AnswerSpec<Answer>>>
        = Question.values().map { question -> question to question.answerSpec }

    val justQuestions: List<Question> = Question.values().toList()

    override fun evaluate(answers: Map<Question, Answer>): Result {
        if (answers.size != questions.size) {
            val unansweredQuestions = justQuestions.toSet() - answers.keys
            throw IllegalArgumentException("All questions need to be answered. These questions are missing answers: $unansweredQuestions")
        }

        val sum = answers.values
            .map { it.score }
            .sum()

        val category = ResultCategory.values()
            .first { category ->
                category.scoreRange.contains(sum)
            }

        return Result(sum, category)
    }
}