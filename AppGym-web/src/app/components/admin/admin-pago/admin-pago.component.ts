import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PagoService } from '../../../services/pago.service';
import { Pago } from '../../../models/pago.model';

@Component({
  selector: 'app-admin-pago',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-pago.component.html',
  styleUrls: ['./admin-pago.component.scss']
})
export class AdminPagoComponent implements OnInit {
  listaPagos: Pago[] = [];
  loading: boolean = false;

  filtroDni: string = '';
  filtroEstado: string = ''; 

  isModalOpen: boolean = false;
  isEditMode: boolean = false;
  pagoSeleccionadoId: string | null = null;

  dniSocio: string = '';
  montoCobro: number | null = null;
  metodoCobro: string = 'Efectivo';

  constructor(
    private pagoService: PagoService, 
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarPagos();
    
    this.route.queryParams.subscribe(params => {
      if (params['abrirNuevo'] === 'true') {
        setTimeout(() => {
          this.abrirModalNuevo();
        }, 100);
      }
    });
  }

  get pagosFiltrados(): Pago[] {
    return this.listaPagos.filter(pago => {
      const cumpleDni = !this.filtroDni || 
        (pago.dniUsuario && pago.dniUsuario.toLowerCase().includes(this.filtroDni.trim().toLowerCase()));

      const cumpleEstado = !this.filtroEstado || 
        (pago.estado && pago.estado.toUpperCase() === this.filtroEstado.toUpperCase());

      return cumpleDni && cumpleEstado;
    });
  }

  cargarPagos(): void {
    this.loading = true;
    this.pagoService.obtenerTodosLosPagos().subscribe({
      next: (data) => {
        this.listaPagos = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la lista de pagos:', err);
        this.loading = false;
      }
    });
  }

  abrirModalNuevo(): void {
    this.isEditMode = false;
    this.pagoSeleccionadoId = null;
    this.dniSocio = '';
    this.montoCobro = null;
    this.metodoCobro = 'Efectivo';
    this.isModalOpen = true;
  }

  abrirModalEditar(pago: Pago): void {
    this.isEditMode = true;
    this.pagoSeleccionadoId = pago.id || null;
    this.montoCobro = pago.monto;
    this.metodoCobro = pago.metodoPago;
    this.dniSocio = '';
    this.isModalOpen = true;
  }

  cerrarModal(): void {
    this.isModalOpen = false;
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { abrirNuevo: null },
      queryParamsHandling: 'merge'
    });
  }

  guardarCobro(): void {
    if (!this.montoCobro || !this.metodoCobro) {
      alert('Por favor complete los campos obligatorios.');
      return;
    }

    if (this.isEditMode && this.pagoSeleccionadoId) {
      this.pagoService.editarCobro(this.pagoSeleccionadoId, this.montoCobro, this.metodoCobro).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargarPagos();
        },
        error: (err) => alert(err.error || 'Ocurrió un error al actualizar el cobro.')
      });
    } else {
      if (!this.dniSocio) {
        alert('El DNI es obligatorio para registrar un nuevo cobro.');
        return;
      }
      this.pagoService.registrarCobro(this.dniSocio, this.montoCobro, this.metodoCobro).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargarPagos();
        },
        error: (err) => alert(err.error || 'Ocurrió un error al registrar el cobro.')
      });
    }
  }

  eliminarPago(id: string): void {
    if (confirm('¿Está seguro de que desea eliminar este registro de cobro de forma permanente?')) {
      this.pagoService.eliminarCobro(id).subscribe({
        next: (mensaje) => {
          alert(mensaje);
          this.cargarPagos();
        },
        error: (err) => alert(err.error || 'No se pudo eliminar el cobro.')
      });
    }
  }
}