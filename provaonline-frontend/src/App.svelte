<script>
 	import { onMount } from 'svelte';
	import { Button } from 'flowbite-svelte';
	import { ArrowLeftOutline, ArrowRightOutline } from 'flowbite-svelte-icons';
	import { Checkbox } from 'flowbite-svelte';
	import { Table, TableBody, TableBodyCell, TableBodyRow, TableHead, TableHeadCell, ImagePlaceholder, Modal } from 'flowbite-svelte';
  	import { slide } from 'svelte/transition';
  	import okIcon from './assets/ok.svg';
  	import wrongIcon from './assets/wrong.svg';
  	
	
	let lstQuestion;
	let id_question;
	let idxCurrentQuestion = 0;
	let textQuestion = "";
	let currentQuestion;
	let selectedOptions = [];
	let lstGuessAnswer = new Array();
	let showResults = false;
	let openRow;
  	let details;
	let jsonGuessResponse;
		
	async function fetchData() {
		const response = await fetch('http://localhost:8080/api/rest/question');
		lstQuestion = await response.json();
		currentQuestion = lstQuestion[0];
		textQuestion = currentQuestion.question_text;	
	}
	onMount(() => {
	    fetchData();
	    
	});
  	
  	const nextQuestion = () => {
  		if (selectedOptions.length === 0) {
  			alert("Please select a question");
  			return;
  		} else {
	  		addGuessAnswer();
			idxCurrentQuestion = idxCurrentQuestion + 1;
			currentQuestion = lstQuestion[idxCurrentQuestion];	
			if (currentQuestion) {
				textQuestion = currentQuestion.question_text;
				id_question = currentQuestion._id;
			}
			selectedOptions = [];
		}	
  	};
  	
  	function addGuessAnswer() {
  		var guessAnswer = new Object();
  			guessAnswer._id = crypto.randomUUID();
			guessAnswer.questionId = currentQuestion._id;
			guessAnswer.guessOptionId = selectedOptions;

		lstGuessAnswer.push(guessAnswer);  		
  	}
  	
  	async function doPostGuesses() {
  		const res = await fetch('http://localhost:8080/api/rest/checkResults', {
			method: 'POST',
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
    		},
			body: JSON.stringify(lstGuessAnswer)
		})
		jsonGuessResponse = await res.json()
		showResults = true;
  	}
  	const toggleRow = (i) => {
    	openRow = openRow === i ? null : i
  	}
  	
  	function getQuestionText(_id) {
  		return lstQuestion.find(item => item._id == _id).question_text;
  	}
  	
  	function getOptionText(_id, _idOption) {
  		return lstQuestion.find(item => item._id == _id).options.find(item => item._id == _idOption).option_txt;
  	}
  	
  	function getOptionValue(_id, _idOption) {
  		return lstQuestion.find(item => item._id == _id).options.find(item => item._id == _idOption).option_value;
  	}  	
</script>

<main>
	 <html lang="pt">
		<head>
			<meta charset="utf-8" />
		</head>
		<body>
			<div style="display: contents">
			{#if showResults === true}
				<Table width="800px;">
				  <TableHead>
				    <TableHeadCell>Question</TableHeadCell>
				    <TableHeadCell>Option selected</TableHeadCell>
				    <TableHeadCell>Correct?</TableHeadCell>
				  </TableHead>
				 <TableBody class="divide-y">
				 {#each jsonGuessResponse as item, i}
				 	<TableBodyRow on:click={() => toggleRow(i)}>
				        <TableBodyCell>{i}</TableBodyCell>
				        <TableBodyCell>{getOptionValue(item.questionId, item.optionIdChoice)}</TableBodyCell>
				        <TableBodyCell align="center">
				        	{#if item.correct}
				        		<img src={okIcon} alt="ok" />
				        	{:else}
				        		<img src={wrongIcon} alt="ok" />
				        	{/if}
				        </TableBodyCell>
				      </TableBodyRow>
				      {#if openRow === i}
				        <TableBodyRow on:dblclick={() => (details = item)}>
				          <TableBodyCell class="p-0" align="left">
				            <div class="px-2 py-3" transition:slide={{ duration: 300, axis: 'y' }}>
				              {getQuestionText(item.questionId)}
				            </div>
				          </TableBodyCell>
				          <TableBodyCell class="p-0" colspan="2" align="left">
				            <div class="px-2 py-3" transition:slide={{ duration: 300, axis: 'y' }}>
				              {getOptionText(item.questionId, item.optionIdChoice)}
				            </div>
				          </TableBodyCell>
				        </TableBodyRow>
				      {/if}
				    {/each}
				  </TableBody>
				</Table>
			{:else if lstQuestion}
				<div style="overflow-x:auto;">
				<span style="font-size: 12px">{@html idxCurrentQuestion}</span>
					<table border='0'>
	            		<tr>
	            			<td colspan="2" align="left">{@html textQuestion}</td>
	            		</tr>
	            		<tr>
	            			<td colspan="2" ><p/></td>
	            		</tr>
	            		
	            		{#if currentQuestion}
	            		{#each currentQuestion.options as option, index}
	            		<tr>
	            			<td colspan="2" align="left">
            					<label>
            						<input type="radio" id="option{index}" bind:group={selectedOptions} value={option._id} />
            						{option.option_txt}
            					</label>
	            			</td>
	            		</tr>
	            		{/each}	
	            		{/if}
	            		<tr>
	            			<td align="left">
								&nbsp;
							</td> 
	            			<td align="right">
		            			{#if idxCurrentQuestion < lstQuestion.length - 1}
									<Button outline on:click={nextQuestion}>Next Question <ArrowRightOutline class="w-5 h-5" /></Button>
								{:else}
									&nbsp;
								{/if}
	            			</td>
	            		</tr>
	            		<tr>
	            			<td colspan="2" align="left">
	            				<button type="button" on:click={doPostGuesses}>Send exam</button>
	            			</td>
	            		</tr>
	            	</table>	
				</div>
			{:else}
    			<p>Loading...</p>
  			{/if}
  			</div>	
		</body>
	</html>
</main>
