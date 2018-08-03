import AbstractPresenter from '../AbstractPresenter';
import Component from "vue-class-component";
const SampleMenu = require("../../../components/side_menu/SampleMenu.vue").default;

@Component({
	components: {
		"sample_menu":SampleMenu
	}
})
export default class AbstractSamplePresenter extends AbstractPresenter{
}