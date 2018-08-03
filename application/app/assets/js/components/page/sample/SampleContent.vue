<template>
	<div>
		<div id="main">
			<section>
				<h2>マイニング企画詳細</h2>
			</section>
			<table class="ta1">
				<tr>
					<th>ID</th>
					<td>1234</td>
				</tr>
				<tr>
					<th>タイトル</th>
					<td>タイトル１２３４</td>
				</tr>
				<tr>
					<th>種類</th>
					<td>一般</td>
				</tr>
				<tr>
					<th>公開日</th>
					<td>2018/5/23</td>
				</tr>
				<tr>
					<th>通知内容</th>
					<td>テストテストテスト</td>
				</tr>
				<tr>
					<th>詳細</th>
					<td>
						テストテストテスト<br/>
						テストテストテスト
					</td>
				</tr>
			</table>

			<p class="c">
				<input type="submit" value="編集" v-on:click="edit()"/>
			</p>
		</div>
		<sample_menu/>
	</div>
</template>


<script lang="ts" src="../../../presenters/page/sample/MiningPlanningContentPresenter.ts"/>
