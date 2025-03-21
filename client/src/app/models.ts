// You may use this file to create any models

export interface Menu {
    id: string;
    name: string;
    description: string;
    price: number;
    quantity: number;
}

export interface Order {
    username: string;
    password: string;
    items: LineItem[]
}

export interface LineItem {
    id: string;
    price: number;
    quantity: number;
}

export interface PaymentDetails {
    paymentId: string;
    orderId: string;
    total: number;
    timestamp: number;
}