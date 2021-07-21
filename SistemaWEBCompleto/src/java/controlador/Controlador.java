/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Carrito;
import modelo.Producto;
import modeloDAO.ProductoDAO;

/**
 *
 * @author root
 */
public class Controlador extends HttpServlet {

    ProductoDAO pdao = new ProductoDAO();
    List<Producto> productos = new ArrayList<>();
    Producto p = new Producto();

    List<Carrito> listarCarrito = new ArrayList<>();
    int item;
    double totalPagar = 0.0;
    int cantidad = 1;

    int idp;
    Carrito car;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        productos = pdao.Listar();

        switch (accion) {
            case "Comprar":
                totalPagar = 0.0;
                idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listarId(idp);
                item = item + 1;

                car = new Carrito();

                car.setItem(item);
                car.setIdProducto(p.getIdProducto());
                car.setNombres(p.getNombres());
                car.setDescripcion(p.getDescripcion());
                car.setPrecioCompra(p.getPrecio());
                car.setCantidad(cantidad);
                car.setSubTotal(cantidad * p.getPrecio());
                listarCarrito.add(car);
                for (int i = 0; i < listarCarrito.size(); i++) {
                    totalPagar = totalPagar + listarCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.setAttribute("carrito", listarCarrito);
                request.setAttribute("contador", listarCarrito.size());
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "AgregarCarrito":
                int pos = 0;
                cantidad = 1;
                idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listarId(idp);
                if (listarCarrito.size() > 0) {
                    for (int i = 0; i < listarCarrito.size(); i++) {
                        if (idp == listarCarrito.get(i).getIdProducto()) {
                            pos = i;
                        }
                    }
                    if (idp == listarCarrito.get(pos).getIdProducto()) {
                        cantidad = listarCarrito.get(pos).getCantidad() + cantidad;
                        double subtotal = listarCarrito.get(pos).getPrecioCompra() * cantidad;
                        listarCarrito.get(pos).setCantidad(cantidad);
                        listarCarrito.get(pos).setSubTotal(subtotal);
                    } else {
                        item = item + 1;
                        car = new Carrito();
                        car.setItem(item);
                        car.setIdProducto(p.getIdProducto());
                        car.setNombres(p.getNombres());
                        car.setDescripcion(p.getDescripcion());
                        car.setPrecioCompra(p.getPrecio());
                        car.setCantidad(cantidad);
                        car.setSubTotal(cantidad * p.getPrecio());
                        listarCarrito.add(car);
                    }

                } else {
                    item = item + 1;
                    car = new Carrito();
                    car.setItem(item);
                    car.setIdProducto(p.getIdProducto());
                    car.setNombres(p.getNombres());
                    car.setDescripcion(p.getDescripcion());
                    car.setPrecioCompra(p.getPrecio());
                    car.setCantidad(cantidad);
                    car.setSubTotal(cantidad * p.getPrecio());
                    listarCarrito.add(car);
                }

                request.setAttribute("contador", listarCarrito.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;

            case "Delete":
                int idproducto = Integer.parseInt(request.getParameter("idp"));
                for (int i = 0; i < listarCarrito.size(); i++) {
                    if (listarCarrito.get(i).getIdProducto() == idproducto) {
                        listarCarrito.remove(i);

                    }

                }
                break;

            case "ActualizarCantidad":
                int idpro = Integer.parseInt(request.getParameter("idp"));
                int cant = Integer.parseInt(request.getParameter("Cantidad"));
                for (int i = 0; i < listarCarrito.size(); i++) {
                    if (listarCarrito.get(i).getIdProducto() == idpro) {
                        listarCarrito.get(i).setCantidad(cant);
                        double st = listarCarrito.get(i).getPrecioCompra() * cant;
                        listarCarrito.get(i).setSubTotal(st);

                    }

                }
                break;

            case "Carrito":
                totalPagar = 0.0;
                request.setAttribute("carrito", listarCarrito);
                for (int i = 0; i < listarCarrito.size(); i++) {
                    totalPagar = totalPagar + listarCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;

            default:
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
