import {AxiosResponse,AxiosError}  from 'axios'
import * as Axios from 'axios'

interface SuccessHandler { (report: any): void }
interface ErrorHandler { (error: any): void }

const AXIOS = Axios.default.create({
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'X-CSRF-Token': $('meta[name=csrf-token]').attr('content'),
    'X-Requested-With': 'XMLHttpRequest',
  },
});

export default class ApiConnection {
  private baseURL:string = 'http://localhost:9000/api/v1'
  private url = (address: string) => `${this.baseURL}/${address}`

  constructor() {}

  // getリクエスト
  async get(url:string, obj: URLSearchParams):Promise<AxiosResponse<any>> {
	return await AXIOS.get(this.url(url),{ params: obj })
  }

  // postリクエスト
  async post(url:string,obj: URLSearchParams) {
    return AXIOS.post(this.url(url), obj)
  }
  // putリクエスト
  async put(url:string,obj: URLSearchParams) {
    return AXIOS.put(this.url(url), obj)
  }
  // deleteリクエスト
  async delete(url:string,obj: URLSearchParams) {
    return AXIOS.delete(this.url(url), { params: obj })
  }
}