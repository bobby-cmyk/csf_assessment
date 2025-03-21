import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { PaymentDetails } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  private restaurantSvc = inject(RestaurantService)
  private router = inject(Router)

  paymentDeets!: PaymentDetails
  // Task 5
  ngOnInit(): void {
    this.paymentDeets = this.restaurantSvc.getPaymentDeets()
  }

  backToMenu() {
    //remove order from local storage
    localStorage.removeItem("order")

    this.router.navigate(["/"])
  }
}
