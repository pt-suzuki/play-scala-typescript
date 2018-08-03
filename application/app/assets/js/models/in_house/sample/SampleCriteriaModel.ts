export default class SampleCriteriaModel{
	private id:number;
	private sample_base_criteria:number;
	private criteria:number;
	private threshold_value:string;

	constructor(){
		this.id = 0
		this.sample_base_criteria = 1;
		this.criteria = 1;
		this.threshold_value = "";
	}
	
    /**
     * Getter $id
     * @return {number}
     */
	public get $id(): number {
		return this.id;
	}

    /**
     * Getter $sample_base_criteria
     * @return {number}
     */
	public get $sample_base_criteria(): number {
		return this.sample_base_criteria;
	}

    /**
     * Getter $criteria
     * @return {number}
     */
	public get $criteria(): number {
		return this.criteria;
	}

    /**
     * Getter $threshold_value
     * @return {string}
     */
	public get $threshold_value(): string {
		return this.threshold_value;
	}

    /**
     * Setter $id
     * @param {number} value
     */
	public set $id(value: number) {
		this.id = value;
	}

    /**
     * Setter $sample_base_criteria
     * @param {number} value
     */
	public set $sample_base_criteria(value: number) {
		this.sample_base_criteria = value;
	}

    /**
     * Setter $criteria
     * @param {number} value
     */
	public set $criteria(value: number) {
		this.criteria = value;
	}

    /**
     * Setter $threshold_value
     * @param {string} value
     */
	public set $threshold_value(value: string) {
		this.threshold_value = value;
	}
	
}