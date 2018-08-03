<template>
	<div>
		<div id="main">
			<section>
				<h2>マイニング企画検索</h2>
			</section>
			<section id="new">
				<h3>統合検索</h3>
				<form method="get" >
					<table class="ta1">
						<tr>
							<th>キーワード入力</th>
							<td>
								<input size="100" type="text" class="wl"/>
							</td>
						</tr>
					</table>
					<p class="c">
						<input type="submit" value="検索" v-on:click="exec()"/>
					</p>
					<h3>詳細検索</h3>
					<table class="ta1">
						<tr>
							<th>種類</th>
							<td>
								<select>
									<option selected="">すべて</option>
									<option>一般</option>
									<option>健保専用</option>
								</select>
							</td>
						</tr>
					</table>
					<table class="ta1">
						<tr>
							<td>
								<select>
									<option>タイトル</option>
									<option>詳細</option>
								</select>
							</td>
							<td>
								<input type="text" size="30" class="ws"/>
							</td>
							<td>
								<select>
									<option>前方一致</option>
									<option>中間一致</option>
									<option>後方一致</option>
									<option>完全一致</option>
								</select>
							</td>
							<td>
								<select>
									<option>AND</option>
									<option>OR</option>
									<option>NOT</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<select>
									<option>タイトル</option>
									<option>詳細</option>
								</select>
							</td>
							<td>
								<input type="text" size="30" class="ws"/>
							</td>
							<td>
								<select>
									<option>前方一致</option>
									<option>中間一致</option>
									<option>後方一致</option>
									<option>完全一致</option>
								</select>
							</td>
							<td>
								<select>
									<option>AND</option>
									<option>OR</option>
									<option>NOT</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<select>
									<option>タイトル</option>
									<option>詳細</option>
								</select>
							</td>
							<td>
								<input type="text" size="30" class="ws"/>
							</td>
							<td>
								<select>
									<option>前方一致</option>
									<option>中間一致</option>
									<option>後方一致</option>
									<option>完全一致</option>
								</select>
							</td>
							<td>
								<select>
									<option>AND</option>
									<option>OR</option>
									<option>NOT</option>
								</select>
							</td>
						</tr>
					</table>
					<table class="ta1">
						<tr>
							<th>作成日</th>
							<td>
								<input type="text" size="30"/>～<input type="text" size="30"/>
							</td>
						</tr>
						<tr>
							<th>公開日</th>
							<td>
								<input type="text" size="30"/>～<input type="text" size="30"/>
							</td>
						</tr>
					</table>
					<p class="c">
						<input type="submit" value="検索" v-on:click="exec()"/>
					</p>
				</form>	
			</section>
		</div>
		<sample_menu/>
	</div>
</template>


<script lang="ts" src="../../../presenters/page/sample/MiningPlanningSearchPresenter.ts"/>
