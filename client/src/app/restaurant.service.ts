import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom, tap } from "rxjs";
import { Menu } from "./models";

@Injectable({ providedIn : "root"})
export class RestaurantService {

  private http = inject(HttpClient)

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems() : Promise<Menu[]> {
    

    const endpoint = "/api/menu"

    return firstValueFrom(this.http.get<Menu[]>(endpoint).pipe(
      tap(response => 
      {
          console.log("API Response:", response)
      })))
  }

  // TODO: Task 3.2
}
