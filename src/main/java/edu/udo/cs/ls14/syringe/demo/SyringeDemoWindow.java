/*
 */
package edu.udo.cs.ls14.syringe.demo;

import edu.udo.cs.ls14.syringe.demo.components.Button;
import edu.udo.cs.ls14.syringe.demo.components.Panel;
import edu.udo.cs.ls14.syringe.interpreter.FreeVariableNotInContextException;
import edu.udo.cs.ls14.syringe.interpreter.InterpreterFactory;
import edu.udo.cs.ls14.syringe.interpreter.TypeError;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.parser.LambdaLexer;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.LambdaContext;
import edu.udo.cs.ls14.syringe.term.parser.TermVisitor;
import edu.udo.cs.ls14.syringe.util.XMLRessourceFactory;
import java.util.Map;
import javax.inject.Provider;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;

/**
 *
 * @author Jan
 */
public class SyringeDemoWindow extends javax.swing.JFrame {
    private final JTextArea errorArea;
    private final LambdaLexer lexer;
    private final LambdaParser parser;    
    private final TermVisitor visitor;
    private final InterpreterFactory interpreterFactory;
    private final XMLRessourceFactory xmlRessourceFactory;
    private final Map<String, Object> context;
    private final GenericXmlApplicationContext applicationContext;
        
    /**
     * Creates new form SyringeDemoWindow
     */
    public SyringeDemoWindow(JTextArea errorArea,
                             LambdaLexer lexer,
                             LambdaParser parser,
                             TermVisitor visitor,
                             InterpreterFactory interpreterFactory,
                             XMLRessourceFactory xmlRessourceFactory,
                             Map<String, Object> context,
                             GenericXmlApplicationContext applicationContext) {
        this.errorArea = errorArea;
        this.lexer = lexer;
        this.parser = parser;
        this.visitor = visitor;
        this.interpreterFactory = interpreterFactory;
        this.xmlRessourceFactory = xmlRessourceFactory;
        this.context = context;
        this.applicationContext = applicationContext;
        initComponents();
    }
    
    private void showRepository() {
        jList1.setListData(context.values().toArray());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = errorArea;
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setText(">");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.CardLayout());
        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        jButton1ActionPerformed(evt);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jPanel2.removeAll();
        jPanel2.doLayout();
        errorArea.setText("");
        lexer.setInputStream(new ANTLRInputStream(jTextField1.getText()));
        parser.setTokenStream(new BufferedTokenStream(lexer));
        LambdaContext parserResult = parser.lambda();
        if (parserResult != null && errorArea.getText().isEmpty()) {
            Term resultTerm = visitor.visit(parserResult).toNormalForm().get();
            errorArea.setText("Normal form:\n");
            errorArea.append(resultTerm.toString());
            errorArea.append("\n");
            try {
                interpreterFactory.interpreter(resultTerm, Provider.class).get().get();
                applicationContext.close();
                applicationContext.destroy();
                ByteArrayResource ressource = xmlRessourceFactory.get();
                applicationContext.load(ressource);
                errorArea.append("XML fragment:\n");
                errorArea.append(new String(ressource.getByteArray()));
                Panel panel = applicationContext.getBean("panel", Panel.class);
                jPanel2.add(panel);
                panel.setVisible(true);
                jPanel2.doLayout();
                panel.doLayout();
            } catch (FreeVariableNotInContextException freeVarEx) {
                errorArea.append(String.format("\n%s", freeVarEx.getMessage()));
            } catch (NoSuchBeanDefinitionException beanEx) {
                errorArea.append(String.format("\n%s", beanEx.getMessage()));
            } catch (TypeError typeEx) {
                errorArea.append(String.format("\n%s", typeEx.getMessage()));
            } catch (IllegalArgumentException iaEx) {
                errorArea.append(String.format("\n%s", iaEx.getMessage()));
            } catch (UnsatisfiedDependencyException uEx) {
                errorArea.append(String.format("\n%s", uEx.getMessage()));
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        
        
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
       
    }//GEN-LAST:event_jTextField1KeyTyped
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SyringeDemoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SyringeDemoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SyringeDemoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SyringeDemoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AnnotationConfigApplicationContext context =
                        new AnnotationConfigApplicationContext(SyringeDemoConfiguration.class);
                SyringeDemoWindow syringeDemoWindow = context.getBean(SyringeDemoWindow.class);
                syringeDemoWindow.showRepository();
                syringeDemoWindow.setVisible(true);                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
