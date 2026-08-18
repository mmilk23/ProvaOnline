<script lang="ts">
  import { onMount } from "svelte";
  import { slide } from "svelte/transition";
  import okIcon from "./assets/ok.svg";
  import wrongIcon from "./assets/wrong.svg";

  type Option = {
    _id: string;
    option_txt: string;
    option_value: string;
    correct: boolean;
  };

  type Question = {
    _id: string;
    question_text: string;
    options: Option[];
  };

  type GuessAnswer = {
    _id: string;
    questionId: string;
    guessOptionId: string;
  };

  type Result = {
    questionId: string;
    optionIdChoice: string;
    correct: boolean;
  };

  let questions: Question[] = [];
  let currentQuestion: Question | undefined;
  let currentQuestionIndex = 0;
  let selectedOption = "";
  let guessAnswers: GuessAnswer[] = [];
  let results: Result[] | undefined;
  let openRow: number | null = null;
  let errorMessage = "";

  onMount(fetchQuestions);

  async function fetchQuestions() {
    try {
      const response = await fetch("/api/rest/question");

      if (!response.ok) {
        throw new Error("Could not load questions");
      }

      questions = await response.json();
      currentQuestion = questions[0];
    } catch (error) {
      errorMessage = error instanceof Error ? error.message : "Could not load questions";
    }
  }

  function requireSelectedOption() {
    if (!selectedOption) {
      errorMessage = "Please select an option before continuing.";
      return false;
    }

    errorMessage = "";
    return true;
  }

  function buildGuessAnswer(): GuessAnswer {
    if (!currentQuestion) {
      throw new Error("No current question selected");
    }

    return {
      _id: crypto.randomUUID(),
      questionId: currentQuestion._id,
      guessOptionId: selectedOption,
    };
  }

  function nextQuestion() {
    if (!requireSelectedOption()) {
      return;
    }

    guessAnswers = [...guessAnswers, buildGuessAnswer()];
    selectedOption = "";
    currentQuestionIndex += 1;
    currentQuestion = questions[currentQuestionIndex];
  }

  async function sendExam() {
    if (!requireSelectedOption()) {
      return;
    }

    const answers = [...guessAnswers, buildGuessAnswer()];
    guessAnswers = answers;

    const response = await fetch("/api/rest/checkResults", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(answers),
    });

    if (!response.ok) {
      errorMessage = "Could not submit exam answers.";
      return;
    }

    results = await response.json();
  }

  function toggleRow(index: number) {
    openRow = openRow === index ? null : index;
  }

  function getQuestion(questionId: string) {
    return questions.find((question) => question._id === questionId);
  }

  function getOption(questionId: string, optionId: string) {
    return getQuestion(questionId)?.options.find((option) => option._id === optionId);
  }
</script>

<main>
  {#if errorMessage}
    <p class="message error">{errorMessage}</p>
  {/if}

  {#if results}
    <section class="results" aria-label="Exam results">
      <table>
        <thead>
          <tr>
            <th>Question</th>
            <th>Option selected</th>
            <th>Correct?</th>
          </tr>
        </thead>
        <tbody>
          {#each results as item, index}
            <tr class="summary-row" onclick={() => toggleRow(index)}>
              <td>{index + 1}</td>
              <td>{getOption(item.questionId, item.optionIdChoice)?.option_value}</td>
              <td class="status">
                {#if item.correct}
                  <img src={okIcon} alt="Correct" />
                {:else}
                  <img src={wrongIcon} alt="Incorrect" />
                {/if}
              </td>
            </tr>
            {#if openRow === index}
              <tr>
                <td colspan="3">
                  <div class="details" transition:slide={{ duration: 200, axis: "y" }}>
                    <strong>{getQuestion(item.questionId)?.question_text}</strong>
                    <span>{getOption(item.questionId, item.optionIdChoice)?.option_txt}</span>
                  </div>
                </td>
              </tr>
            {/if}
          {/each}
        </tbody>
      </table>
    </section>
  {:else if currentQuestion}
    <section class="exam" aria-label="Current question">
      <p class="counter">{currentQuestionIndex + 1} / {questions.length}</p>
      <h1>{currentQuestion.question_text}</h1>

      <fieldset>
        <legend>Options</legend>
        {#each currentQuestion.options as option, index}
          <label>
            <input
              id={`option-${index}`}
              type="radio"
              bind:group={selectedOption}
              value={option._id}
            />
            <span>{option.option_txt}</span>
          </label>
        {/each}
      </fieldset>

      <div class="actions">
        {#if currentQuestionIndex < questions.length - 1}
          <button type="button" onclick={nextQuestion}>Next Question</button>
        {:else}
          <button type="button" onclick={sendExam}>Send exam</button>
        {/if}
      </div>
    </section>
  {:else}
    <p class="message">Loading...</p>
  {/if}
</main>