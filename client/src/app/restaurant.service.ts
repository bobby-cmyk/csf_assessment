import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom, tap } from "rxjs";
import { LineItem, Menu, Order, PaymentDetails } from "./models";

@Injectable({ providedIn : "root"})
export class RestaurantService {

  private http = inject(HttpClient)

  paymentDeets!: PaymentDetails

  // Task 2.2
  // You change the method's signature but not the name
  getMenuItems() : Promise<Menu[]> {
  
    const endpoint = "/api/menu"

    return firstValueFrom(this.http.get<Menu[]>(endpoint).pipe(
      tap(response => 
      {
          console.log("API Response:", response)
      })))
  }

  // Task 3.2
  submitOrder(username: string, password: string): Promise<PaymentDetails> | undefined{
    const endpoint = "/api/food_order"

    const orderString = localStorage.getItem("order")

    let items : LineItem[] = []
    let order : Order

    if (orderString) {
      const itemsArr = JSON.parse(orderString)

      for (var i = 0; i < itemsArr.length; i++) {
        var item = itemsArr[i];
        items.push(
          {
            id: item.id,
            price: item.price,
            quantity: item.quantity
          })
    }

      order = {
        username: username,
        password: password,
        items: items
      }

      console.log(order)
      return firstValueFrom(this.http.post<PaymentDetails>(endpoint, order))
        .then(paymentDeets => this.paymentDeets = paymentDeets)
    }

    return
  }

  getPaymentDeets() : PaymentDetails{
    return this.paymentDeets;
  }

}
