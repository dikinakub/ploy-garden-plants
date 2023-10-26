import { Injectable } from '@angular/core';
import { catchError, map } from 'rxjs/operators'
import { Observable, throwError } from 'rxjs'
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})

export class CrudService {

  // Node/Express API
  REST_API: string = 'http://localhost:8080/api';

  // Http header
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  getCustomers(): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAll`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }

  getCustomerByName(name: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findByName/${name}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }

  getAddressAll(): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAddressAll`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }

  getAddressByKey(key: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAddress/${key}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }

  deleteCustomer(id: any): any {
    let API_URL = `${this.REST_API}/customer/delete/${id}`;
    return this.http.post(API_URL, { headers: this.httpHeaders })
      .pipe(
        catchError(this.handleError)
      )
  }

  // Error
  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Handle client error
      errorMessage = error.error.message;
    } else {
      // Handle server error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
