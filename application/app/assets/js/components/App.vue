<template>
	<div>
		<nav id="menubar">
			<ul>
				<li>
					<router-link to="/sample">サンプル１</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル２</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル３</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル４</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル５</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル６</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル７</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル８</router-link>
				</li>
			</ul>
		</nav>
		<nav id="menubar-s">
			<ul>
				<li>
					<router-link to="/sample">サンプル１</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル２</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル３</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル４</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル５</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル６</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル７</router-link>
				</li>
				<li>
					<router-link to="/sample">サンプル８</router-link>
				</li>
			</ul>
		</nav>
		<div id="contents">
			<router-view />
			<p id="pagetop">
				<a href="#">↑ PAGE TOP</a>
			</p>
		</div>
	</div>
</template>
