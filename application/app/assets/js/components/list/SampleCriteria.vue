<template>
	<div>
		<table class="ta1">
			<tr>
				<th>参照</th>
				<td>
					<select v-model="criteria.mining_base_criteria" v-on:change="replacement_criteria">
						<option v-for="select_base_criteria in select_base_criteria_list" v-bind:key="select_base_criteria.code" v-bind:value="select_base_criteria.code">{{select_base_criteria.name}}</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>対象項目</th>
				<td>
					<select v-model="criteria.criteria" >
						<option v-for="select_criteria in select_criteria_list" v-bind:key="select_criteria.code" v-bind:value="select_criteria.code">{{select_criteria.name}}</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>閾値</th>
				<td>
					<input type="text" v-model="criteria.threshold_value"/>
				</td>
			</tr>
		</table>
	</div>
</template>

<script lang="ts" src="../../presenters/list/MiningPlanningCriteriaPresenter.ts"/>