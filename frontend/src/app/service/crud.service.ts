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
  // REST_API: string = '/api';

  // Http header
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  // ********* Customer *********
  getCustomers(): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAll`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getCustomer(id: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findById/${id}`;
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
  checkCustomerByName(name: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/checkCustomer/${name}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getProvincesAll(): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findProvincesAll`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getProvincesByKey(key: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findProvinces/${key}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getAmphuresByProvincesId(provincesId: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAmphuresByProvincesId/${provincesId}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getAmphuresByProvincesIdAndNameTh(provincesId: any, key: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findAmphures/${provincesId}/${key}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getTambonsByAmphureId(amphureId: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findTambonsByAmphureId/${amphureId}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getTambonsByAmphureIdAndNameTh(amphureId: any, key: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findTambons/${amphureId}/${key}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getTambonsById(id: any): Observable<any> {
    let API_URL = `${this.REST_API}/customer/findTambonsById/${id}`;
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
  updateCustomerDefaultFlag(id: any): any {
    let API_URL = `${this.REST_API}/customer/updateCustomerDefaultFlag/${id}`;
    return this.http.post(API_URL, { headers: this.httpHeaders })
      .pipe(
        catchError(this.handleError)
      )
  }

  // ********* Stock *********
  getStockByType(type: any): Observable<any> {
    let API_URL = `${this.REST_API}/stock/findByType/${type}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getStock(id: any): Observable<any> {
    let API_URL = `${this.REST_API}/stock/findById/${id}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  getStockByName(name: any): Observable<any> {
    let API_URL = `${this.REST_API}/stock/findByName/${name}`;
    return this.http.get(API_URL, { headers: this.httpHeaders })
      .pipe(map((res: any) => {
        return res || {}
      }),
        catchError(this.handleError))
  }
  deleteStock(id: any): any {
    let API_URL = `${this.REST_API}/stock/delete/${id}`;
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
