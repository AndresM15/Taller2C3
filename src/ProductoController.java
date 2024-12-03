import java.util.ArrayList;
import java.util.List;
public class ProductoController {
    private List<Producto> listaProductos;

    public ProductoController() {
        listaProductos = new ArrayList<>();
    }

    // Guardar un producto
    public void guardarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    // Obtener la lista de productos
    public List<Producto> obtenerListaProductos() {
        return listaProductos;
    }

    // Modificar un producto
    public boolean modificarProducto(int codigo, Producto productoModificado) {
        for (Producto producto : listaProductos) {
            if (producto.getCodigo() == codigo) {
                producto.setNombre(productoModificado.getNombre());
                producto.setPrecio(productoModificado.getPrecio());
                producto.setCategoria(productoModificado.getCategoria());
                return true;
            }
        }
        return false;
    }

    // Eliminar un producto
    public boolean eliminarProducto(int codigo) {
        return listaProductos.removeIf(producto -> producto.getCodigo() == codigo);
    }
}
