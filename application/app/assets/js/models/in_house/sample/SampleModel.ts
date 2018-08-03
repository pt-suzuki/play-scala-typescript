import SampleCriteriaModel from './SampleCriteriaModel'

export default class SampleModel{
	private id:number;
	private title:string;
	private release_date:string;
	private notification_detail:string;
	private detail:string;
	private sample_criteria_list:SampleCriteriaModel[];

	constructor(){
		this.id = 0
		this.title = "";
		this.release_date = "";
		this.notification_detail = ""
		this.detail = ""
		this.sample_criteria_list = [new SampleCriteriaModel]
	}


    /**
     * Getter $id
     * @return {number}
     */
	public get $id(): number {
		return this.id;
	}

    /**
     * Getter $title
     * @return {string}
     */
	public get $title(): string {
		return this.title;
	}

    /**
     * Getter $release_date
     * @return {string}
     */
	public get $release_date(): string {
		return this.release_date;
	}

    /**
     * Getter $notification_detail
     * @return {string}
     */
	public get $notification_detail(): string {
		return this.notification_detail;
	}

    /**
     * Getter $detail
     * @return {string}
     */
	public get $detail(): string {
		return this.detail;
	}

    /**
     * Getter $sample_criteria_list
     * @return {SampleCriteriaModel[]}
     */
	public get $sample_criteria_list(): SampleCriteriaModel[] {
		return this.sample_criteria_list;
	}

    /**
     * Setter $id
     * @param {number} value
     */
	public set $id(value: number) {
		this.id = value;
	}

    /**
     * Setter $title
     * @param {string} value
     */
	public set $title(value: string) {
		this.title = value;
	}

    /**
     * Setter $release_date
     * @param {string} value
     */
	public set $release_date(value: string) {
		this.release_date = value;
	}

    /**
     * Setter $notification_detail
     * @param {string} value
     */
	public set $notification_detail(value: string) {
		this.notification_detail = value;
	}

    /**
     * Setter $detail
     * @param {string} value
     */
	public set $detail(value: string) {
		this.detail = value;
	}

    /**
     * Setter $sample_criteria_list
     * @param {SampleCriteriaModel[]} value
     */
	public set $sample_criteria_list(value: SampleCriteriaModel[]) {
		this.sample_criteria_list = value;
	}
	
}