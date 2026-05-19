import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago } from '../models/pago.model';

@Injectable({
  providedIn: 'root'
})
export class PagoService {
  private API_PAGOS = 'http://localhost:8080/api/pagos';
  private API_COBROS = 'http://localhost:8080/api/cobros';

  constructor(private http: HttpClient) {}

  // Consolidado completo para la administración (Admin)
  obtenerTodosLosPagos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.API_PAGOS}/todos`);
  }

  // Historial específico del socio autenticado
  obtenerMisPagos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(`${this.API_PAGOS}/mis-pagos`);
  }

  // POST: Registrar cobro mandando DNI, monto y metodo como params
  registrarCobro(dni: string, monto: number, metodo: string): Observable<any> {
    const url = `${this.API_COBROS}/registrar?dni=${dni}&monto=${monto}&metodo=${metodo}`;
    return this.http.post(url, {});
  }

  // PUT: Editar cobro existente pasando id en la ruta y monto/metodo como params
  editarCobro(id: string, monto: number, metodo: string): Observable<any> {
    const url = `${this.API_COBROS}/editar/${id}?monto=${monto}&metodo=${metodo}`;
    return this.http.put(url, {});
  }

  // DELETE: Eliminar un cobro por ID (Retorna texto plano "Cobro eliminado correctamente.")
  eliminarCobro(id: string): Observable<string> {
    return this.http.delete(`${this.API_COBROS}/eliminar/${id}`, { responseType: 'text' });
  }
}