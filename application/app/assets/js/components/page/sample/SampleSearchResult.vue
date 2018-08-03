<template>
	<div>
		<div id="main">
    <section>

        <h2>マイニング企画一覧</h2>

        <h3>検索条件</h3>
    </section>

    
    <section id="new">

        <table class="ta1">
            <tr>
                <th class="tamidashi">No.</th>
                <th class="tamidashi">名称</th>
                <th class="tamidashi">説明</th>
            </tr>
            <tr>
                <th>1</th>
                <td>
					<router-link to="/sample/content">見出し</router-link>
				</td>
                <td><a href="http://tem\plate-party.com/file/pickup_googlemap.html">Google Mapを使いたい人はこちらの解説をご覧下さい。</a></td>
            </tr>
            <tr>
                <th>2</th>
				<td>
					<router-link to="/sample/content">見出し</router-link>
				</td>
                <td>ここに説明など入れて下さい。サンプルテキスト。</td>
            </tr>
            <tr>
                <th>3</th>
				<td>
					<router-link to="/sample/content">見出し</router-link>
				</td>
                <td>ここに説明など入れて下さい。サンプルテキスト。</td>
            </tr>
            <tr>
                <th>4</th>
				<td>
					<router-link to="/sample/content">見出し</router-link>
				</td>
                <td>ここに説明など入れて下さい。サンプルテキスト。</td>
            </tr>
            <tr>
                <th>5</th>
				<td>
					<router-link to="/sample/content">見出し</router-link>
				</td>
                <td>ここに説明など入れて下さい。サンプルテキスト。</td>
            </tr>
        </table>
    </section>

    </div>

		<sample_menu/>
	</div>
</template>


<script lang="ts" src="../../../presenters/page/sample/MiningPlanningSearchResultPresenter.ts"/>
