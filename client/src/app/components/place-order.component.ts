import { Component, inject, OnInit } from '@angular/core';
import { Menu, PaymentDetails } from '../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant.service';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{
  //Task 3

  private fb = inject(FormBuilder)
  private router = inject(Router)
  private restaurantSvc = inject(RestaurantService)

  order!: Menu[]
  totalPrice: number = 0
  orderForm!: FormGroup

  ngOnInit(): void {
    this.orderForm = this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      password: this.fb.control<string>('', [Validators.required])
    })

    let orderString = localStorage.getItem("order")
    if (orderString) {
      this.order = JSON.parse(orderString)
    }
    this.order.forEach(item => this.totalPrice += item.price * item.quantity)
  }

  startOver() {
    localStorage.removeItem("order")
    this.router.navigate(['/'])
  }

  async confirmOrder() {
    const username = this.orderForm.get("username")?.value
    const password = this.orderForm.get("password")?.value

    console.log("Username: ", username)
    console.log("Password: ", password)

    try {
      const paymentDeets : PaymentDetails | undefined = await this.restaurantSvc.submitOrder(username, password)
      console.log(paymentDeets)
      this.router.navigate(['/confirm']) 
    }

    catch (e) {
      if (e instanceof Error) {
        alert(e.message)
      }
    }
  }

}
