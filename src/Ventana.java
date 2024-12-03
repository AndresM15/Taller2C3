import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
public class Ventana extends JFrame{
    private JTextField codigotxt, nombretxt, preciotxt, categoriatxt;
    private JTable tablaProductos;
    private ProductoController productoController;

    public Ventana() {
        productoController = new ProductoController();
        initComponents();
    }

    private void initComponents() {
        // Configuración principal del JFrame
        this.setTitle("Gestión de Productos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout(10, 10));

        // Panel superior (Formulario)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel codigoLbl = new JLabel("Código:");
        codigotxt = new JTextField(15);

        JLabel nombreLbl = new JLabel("Nombre:");
        nombretxt = new JTextField(15);

        JLabel precioLbl = new JLabel("Precio:");
        preciotxt = new JTextField(15);

        JLabel categoriaLbl = new JLabel("Categoría:");
        categoriatxt = new JTextField(15);

        // Añadir los componentes al panel del formulario
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(codigoLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFormulario.add(codigotxt, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(nombreLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(nombretxt, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(precioLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelFormulario.add(preciotxt, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(categoriaLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panelFormulario.add(categoriatxt, gbc);

        // Panel inferior (Botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton guardarBtn = new JButton("Guardar");
        JButton modificarBtn = new JButton("Modificar");
        JButton eliminarBtn = new JButton("Eliminar");

        guardarBtn.addActionListener(this::guardarProducto);
        modificarBtn.addActionListener(this::modificarProducto);
        eliminarBtn.addActionListener(this::eliminarProducto);

        panelBotones.add(guardarBtn);
        panelBotones.add(modificarBtn);
        panelBotones.add(eliminarBtn);

        // Tabla
        tablaProductos = new JTable(new DefaultTableModel(new Object[]{"Código", "Nombre", "Precio", "Categoría"}, 0));
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Añadir componentes al JFrame
        this.add(panelFormulario, BorderLayout.NORTH);
        this.add(panelBotones, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.SOUTH);

        // Hacer visible la ventana
        this.setVisible(true);
    }

    private void guardarProducto(ActionEvent evt) {
        try {
            int codigo = Integer.parseInt(codigotxt.getText());
            String nombre = nombretxt.getText();
            double precio = Double.parseDouble(preciotxt.getText());
            String categoria = categoriatxt.getText();

            Producto producto = new Producto(codigo, nombre, precio, categoria);
            productoController.guardarProducto(producto);

            actualizarTabla();
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProducto(ActionEvent evt) {
        try {
            int codigo = Integer.parseInt(codigotxt.getText());
            String nombre = nombretxt.getText();
            double precio = Double.parseDouble(preciotxt.getText());
            String categoria = categoriatxt.getText();

            Producto productoModificado = new Producto(codigo, nombre, precio, categoria);
            if (productoController.modificarProducto(codigo, productoModificado)) {
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto(ActionEvent evt) {
        try {
            int codigo = Integer.parseInt(codigotxt.getText());
            if (productoController.eliminarProducto(codigo)) {
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        List<Producto> lista = productoController.obtenerListaProductos();
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);

        for (Producto producto : lista) {
            modelo.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCategoria()});
        }
    }

    private void limpiarCampos() {
        codigotxt.setText("");
        nombretxt.setText("");
        preciotxt.setText("");
        categoriatxt.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ventana::new);
    }
}
