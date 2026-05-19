export interface Pago {
  id?: string;
  usuarioId: string;        // Referencia al ID o Email del Socio
  monto: number;           // Monto del abono
  fechaPago?: string;      // LocalDateTime de Spring (formato ISO)
  fechaVencimiento?: string; // Cuándo vence la cuota
  metodoPago: string;      // Ej: "Efectivo", "Tarjeta"
  estado: string;          // "PAGADO", "PENDIENTE", "VENCIDO"

  nombreUsuario?: string;
  dniUsuario?: string;
  emailUsuario?: string;
}