export default class DropDownModel{
	private code:string;
	private name:string;

	constructor(){
		this.code = "";
		this.name = "";
	}

    /**
     * Getter $code
     * @return {string}
     */
	public get $code(): string {
		return this.code;
	}

    /**
     * Getter $name
     * @return {string}
     */
	public get $name(): string {
		return this.name;
	}

    /**
     * Setter $code
     * @param {string} value
     */
	public set $code(value: string) {
		this.code = value;
	}

    /**
     * Setter $name
     * @param {string} value
     */
	public set $name(value: string) {
		this.name = value;
	}

	
}