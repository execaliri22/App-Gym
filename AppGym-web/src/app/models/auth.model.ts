//  LoginRequest.java 
export interface LoginRequest {
  email: string;
  password: string;
}

//  RegistroRequest.java 
export interface RegistroRequest {
  dni: string;
  nombreCompleto: string;
  email: string;
  password: string;
  objetivo: string; 
  sede: string;
}

// AuthResponse.java 
export interface AuthResponse {
  token: string;
  email: string;
  nombre: string;
}