import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();
        boolean sucesso = false;

        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco!");
            return false;
        }

        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                sucesso = true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Nenhum produto foi cadastrado!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return sucesso;
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        conn = new conectaDAO().connectDB();

        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco!");
            return listagem;
        }

        try {
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return listagem;
    }

    public boolean venderProduto(int id) {
        conn = new conectaDAO().connectDB();
        boolean sucesso = false;

        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco!");
            return false;
        }

        try {
            String sqlVerifica = "SELECT * FROM produtos WHERE id = ? AND status = 'A Venda'";
            prep = conn.prepareStatement(sqlVerifica);
            prep.setInt(1, id);
            resultset = prep.executeQuery();

            if (!resultset.next()) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado ou já vendido!");
                return false;
            }

            String sqlAtualiza = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(sqlAtualiza);
            prep.setInt(1, id);

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                sucesso = true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao vender produto!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return sucesso;
    }

    public ArrayList<ProdutosDTO> listarVendidos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        conn = new conectaDAO().connectDB();

        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco!");
            return listagem;
        }

        try {
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return listagem;
    }
}