import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.TableColumnModel;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object menu
        ProductMenu menu = new ProductMenu();

        // atur ukuran menu
        menu.setSize(700, 600);

        // letakkan menu di tengah layar
        menu.setLocationRelativeTo(null);

        // isi menu
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.LIGHT_GRAY);

        // tampilkan menu
        menu.setVisible(true);

        // agar program ikut berhenti saat menu diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private Database database;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JRadioButton semuaUmurRadioButton;
    private JRadioButton remajaRadioButton;
    private JRadioButton dewasaRadioButton;
    private ButtonGroup ratingGroup;

    // constructor
    public ProductMenu() {
        // inisialisasi listProduct
        database = new Database();

        // inisialisasi dan grouping Radio Button
        ratingGroup = new ButtonGroup();
        ratingGroup.add(semuaUmurRadioButton);
        ratingGroup.add(remajaRadioButton);
        ratingGroup.add(dewasaRadioButton);

        productTable.setModel(setTable());

        // isi tabel produk
        productTable.setModel(setTable());

        aturLebarKolom();

        // styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Action", "Adventure", "Fighting", "Indie", "RPG", "Simulation", "Sports", "Strategy" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    // mode add
                    insertData();
                } else {
                    // mode update
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(null,
                        "Yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION);

                // Jika user mengklik "Yes", maka hapus data
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex,1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex,2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex,3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex,4).toString();
                String curRating = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);

                if (curRating.equals("Semua Umur")) semuaUmurRadioButton.setSelected(true);
                else if (curRating.equals("Remaja")) remajaRadioButton.setSelected(true);
                else dewasaRadioButton.setSelected(true);

                // button update data
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID", "Nama", "Harga", "Kategori", "Rating Usia" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");
            int i = 1;
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = i;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("ratingUsia");
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return tmp;
    }

    // Method helper untuk mendapatkan teks dari radio button yang dipilih
    public String getSelectedRating() {
        if (semuaUmurRadioButton.isSelected()) {
            return "Semua Umur";
        } else if (remajaRadioButton.isSelected()) {
            return "Remaja";
        } else if (dewasaRadioButton.isSelected()) {
            return "Dewasa";
        }
        return ""; // Kembalikan string kosong jika tidak ada yang dipilih
    }

    public void insertData() {
        String id = idField.getText();
        String nama = namaField.getText();
        String hargaStr = hargaField.getText();
        String kategori = kategoriComboBox.getSelectedItem().toString();
        String rating = getSelectedRating();

        // Cek jika ada field yang kosong
        if (id.isEmpty() || nama.isEmpty() || hargaStr.isEmpty() || kategori.equals("???") || rating.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek jika ID sudah ada
        try {
            ResultSet resultSet = database.selectQuery("SELECT COUNT(*) FROM product WHERE id = '" + id + "'");
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "ID Produk sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            String sql = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + rating + "')";
            database.insertUpdateDeleteQuery(sql);

            productTable.setModel(setTable());
            aturLebarKolom();
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateData() {
        String id = idField.getText();
        String nama = namaField.getText();
        String hargaStr = hargaField.getText();
        String kategori = kategoriComboBox.getSelectedItem().toString();
        String rating = getSelectedRating();

        // Cek jika ada field yang kosong
        if (nama.isEmpty() || hargaStr.isEmpty() || kategori.equals("???") || rating.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom kecuali ID harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            String sql = "UPDATE product SET nama = '" + nama + "', harga = " + harga + ", kategori = '" + kategori + "', ratingUsia = '" + rating + "' WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sql);

            productTable.setModel(setTable());
            aturLebarKolom();
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        String id = productTable.getModel().getValueAt(selectedIndex, 1).toString();
        String sql = "DELETE FROM product WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sql);

        // Update tabel (Memuat ulang dari database)
        productTable.setModel(setTable());

        aturLebarKolom();

        // Bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        ratingGroup.clearSelection();

        // Button Add
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void aturLebarKolom() {
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(100);
    }

}