import AbstractPresenter from '../AbstractPresenter';
import Component from "vue-class-component";
const SettingsMenu = require("../../../components/side_menu/SettingsMenu.vue").default;

@Component({
	components: {
		"settings_menu": SettingsMenu
	}
})
export default class AbstractSettingPresenter extends AbstractPresenter{
	
}