<template>
	<div>
		<div id="main">
			<section>
				<h2>マイニング企画新規登録</h2>
			</section>
			<div id="top">
				<table class="ta1">
					<tr>
						<th>タイトル</th>
						<td>
							<input type="text" name="お名前" size="30" class="ws" v-bind:value="item.title"/>
						</td>
					</tr>
					<tr>
						<th>公開日</th>
						<td>
							<input type="text" size="12" v-bind:value="item.release_date"/>
						</td>
					</tr>
					<tr>
						<th>通知内容</th>
						<td>
							<input type="text" size="30" class="wl" v-bind:value="item.notification_detail"/>
						</td>
					</tr>
					<tr>
						<th>詳細</th>
						<td>
							<textarea name="お問い合わせ詳細" cols="30" rows="10" class="wl"  v-bind:value="item.detail"></textarea>
						</td>
					</tr>
				</table>
				<ul>
					<li v-for="criteria in item.sample_criteria_list">
						<sample_criteria :criteria="criteria" :select_base_criteria_list="select_base_criteria_list"></sample_criteria>
						<table>
							<tr>
								<td>
									<button type="button" v-on:click="append(criteria)">追加</button>
								</td>
								<td>
									<button type="button" v-on:click="remove(criteria)" v-if="item.sample_criteria_list.length > 1">削除</button>
								</td>
							</tr>
						</table>
					</li>
				</ul>
				<p class="c">
					<input type="submit" value="登録" v-on:click="submit()"/>
					<input type="submit" value="キャンセル" v-on:click="cancel()"/>
				</p>
			</div>
		</div>
		<sample_menu/>
	</div>
</template>


<script lang="ts" src="../../../presenters/page/sample/MiningPlanningEditPresenter.ts"/>
