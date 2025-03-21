import { Component, inject, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Menu } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{

  //Task 2
  private restaurantSvc = inject(RestaurantService)
  private router = inject(Router)
  
  menus!: Menu[]
  totalQuantity: number = 0
  totalPrice:number = 0

  ngOnInit(): void {
    this.restaurantSvc.getMenuItems().then(menus => {
        this.menus = menus
        this.menus.forEach(menu => menu.quantity = 0)
      }  
    )
  }

  changeQuantity(delta : number, id : string) {
    //https://stackoverflow.com/questions/4689856/how-to-change-value-of-object-which-is-inside-an-array-using-javascript-or-jquer
    const menuIndex = this.menus.findIndex(obj => obj.id == id)

    console.log("Before update: " + this.menus[menuIndex])

    console.log("Menu: " + this.menus[menuIndex].name)

    if (!(this.menus[menuIndex].quantity == 0 && delta < 0)) {
      this.menus[menuIndex].quantity += delta;
      this.menus = [...this.menus]

      console.log(this.menus)

      let newTotalQuantity = 0;
      let newTotalPrice = 0

      this.menus.forEach(menu => {
        newTotalQuantity += menu.quantity
        newTotalPrice += (menu.price * menu.quantity)
      })

      this.totalQuantity = newTotalQuantity
      this.totalPrice = newTotalPrice

      console.log("Total price: " + this.totalPrice)
      console.log("Total price: " + this.totalPrice)
    }
  }

  placeOrder() {
      let order = this.menus.filter(menu => menu.quantity > 0)
      localStorage.setItem("order", JSON.stringify(order))
      this.router.navigate(['/place-order'])
  }
}
