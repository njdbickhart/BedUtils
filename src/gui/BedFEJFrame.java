/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import file.BedAbstract;
import file.BedMap;
import file.BedSimple;
import implement.BedVariable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.BedIntersect;
import utils.LineIntersect;

/**
 *
 * @author desktop
 */
public class BedFEJFrame extends javax.swing.JFrame {
    private int runcount = 1;

    /**
     * Creates new form BedFEJFrame
     */
    public BedFEJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileSelector = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        intersectChr = new javax.swing.JTextField();
        intersectStart = new javax.swing.JTextField();
        intersectEnd = new javax.swing.JTextField();
        intersectInput = new javax.swing.JTextField();
        intersectOutput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        intersectCompFile = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        CompFileChooserButton = new javax.swing.JButton();
        InputFileChooserButton = new javax.swing.JButton();
        OutputFileChooserButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java BedTools");

        intersectChr.setPreferredSize(new java.awt.Dimension(6, 22));

        intersectStart.setToolTipText("");

        intersectEnd.setToolTipText("");
        intersectEnd.setPreferredSize(new java.awt.Dimension(6, 22));

        intersectOutput.setToolTipText("");

        jLabel1.setText("Chr");
        jLabel1.setToolTipText("");

        jLabel2.setText("Start");
        jLabel2.setToolTipText("");

        jLabel3.setText("End");
        jLabel3.setToolTipText("");

        jLabel4.setText("Input");
        jLabel4.setToolTipText("This is an optional field. Select an input file to compare against the Comparison file.");

        jLabel5.setText("Output");
        jLabel5.setToolTipText("");

        jButton1.setText("Run");
        jButton1.setToolTipText("Run Bed File Intersection");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunIntersect(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearIntersect(evt);
            }
        });

        intersectCompFile.setToolTipText("");

        jLabel6.setText("Comparison File");
        jLabel6.setToolTipText("Mandatory input bed file. This is the bed file that the \"input\" or individual coordinates (ie. \"chr\", \"start\" and \"end\") will be compared against.");

        CompFileChooserButton.setText("...");
        CompFileChooserButton.setToolTipText("");
        CompFileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompFileChooserButtonActionPerformed(evt);
            }
        });

        InputFileChooserButton.setText("...");
        InputFileChooserButton.setToolTipText("");
        InputFileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputFileChooserButtonActionPerformed(evt);
            }
        });

        OutputFileChooserButton.setText("...");
        OutputFileChooserButton.setToolTipText("");
        OutputFileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutputFileChooserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 64, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(12, 12, 12)
                                        .addComponent(intersectChr, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(intersectStart, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(intersectEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(intersectCompFile, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CompFileChooserButton))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(intersectOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(OutputFileChooserButton))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(intersectInput, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(InputFileChooserButton)))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intersectCompFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(CompFileChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(intersectStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intersectEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intersectChr, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intersectInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InputFileChooserButton)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(intersectOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OutputFileChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Intersect", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Intersect");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RunIntersect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunIntersect
        //Parity checking for intersection
        boolean chrField = !this.intersectChr.getText().equals("");
        boolean startField = !this.intersectStart.getText().equals("");
        boolean endField = !this.intersectEnd.getText().equals("");
        boolean compField = !this.intersectCompFile.getText().equals("");
        boolean inputField = !this.intersectInput.getText().equals("");
        boolean outputField = !this.intersectOutput.getText().equals("");
        
        JFrame dialogFrame = new JFrame("Message Box");
        
        // Only mandatory, non-toggleable field
        if(!compField){
            JOptionPane.showMessageDialog(dialogFrame, "Must enter a comparison BedFile in field: \"Comparison File!\"");
            return;
        }
        
        // Check if comp file exists
        Path compFile = Paths.get(this.intersectCompFile.getText());
        if(! compFile.toFile().exists()){
            JOptionPane.showMessageDialog(dialogFrame, "Could not find Comparison File: " + this.intersectCompFile.getText());
            return;
        }
        
        BedMap<BedVariable> comp = new BedMap<>();        
        try(BufferedReader input = Files.newBufferedReader(compFile, Charset.defaultCharset())){
            String line;
            while((line = input.readLine()) != null){
                line = line.trim();
                String[] segs = line.split("\t");
                BedVariable bed;
                if(segs.length > 3){
                    bed = new BedVariable(segs[0], Integer.parseInt(segs[1]), Integer.parseInt(segs[2]), Arrays.copyOfRange(segs, 3, segs.length));
                }else{
                    bed = new BedVariable(segs[0], Integer.parseInt(segs[1]), Integer.parseInt(segs[2]));
                }
                comp.addBedData(bed);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(dialogFrame, "Error reading from Bed file: " + this.intersectCompFile.getText());
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialogFrame, "Improperly formatted Bed file! Please check: " + this.intersectCompFile.getText());
            return;
        }
        
        ArrayList<BedAbstract> results = null;
        
        // Individual region takes priority
        if(chrField || startField || endField){
            if(!chrField){
                JOptionPane.showMessageDialog(dialogFrame, "If using individual line intersection, please enter: \"Chr\" field!");
                return;
            }else if(!startField){
                JOptionPane.showMessageDialog(dialogFrame, "If using individual line intersection, please enter: \"Start\" field!");
                return;
            }else if(!endField){
                JOptionPane.showMessageDialog(dialogFrame, "If using individual line intersection, please enter: \"End\" field!");
                return;
            }
            int iStart = Integer.parseInt(this.intersectStart.getText());
            int iEnd = Integer.parseInt(this.intersectEnd.getText());
            results = LineIntersect.returnIntersect(comp, this.intersectChr.getText(), iStart, iEnd);
        }else if(inputField){
            Path inputFFile = Paths.get(this.intersectInput.getText());
            if(!inputFFile.toFile().exists()){
                JOptionPane.showMessageDialog(dialogFrame, "Error! Could not open input file: " + this.intersectInput.getText());
                return;
            }
            
            BedMap intersection = BedIntersect.intersectFileBed(inputFFile, comp);
            results = new ArrayList<>();
            for(Object chr : intersection.getListChrs()){
                String c = (String) chr;
                ArrayList<BedAbstract> temp = intersection.getSortedBedAbstractList(c);
                results.addAll(temp);
            }
        }
        
        // Now it's time to handle the results
        if(!outputField){
            // We're printing out to a table
            BedFEOutput resultsTable = new BedFEOutput();
            resultsTable.addBedData(results, "trial " + runcount);
            runcount++;
            
            resultsTable.setVisible(true);
        }else{
            Path outp = Paths.get(this.intersectOutput.getText());            
            try(BufferedWriter out = Files.newBufferedWriter(outp, Charset.defaultCharset())){
                for(BedAbstract b : results){
                    BedVariable s = (BedVariable) b;
                    
                    out.write(s.getOutStr());
                    out.write(System.lineSeparator());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialogFrame, "Could not write to output file: " + this.intersectOutput.getText());
                return;
            }
            JOptionPane.showMessageDialog(dialogFrame, "Results in output file: " + outp.toString());
        }
    }//GEN-LAST:event_RunIntersect

    private void ClearIntersect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearIntersect
        this.intersectChr.setText("");
        this.intersectCompFile.setText("");
        this.intersectStart.setText("");
        this.intersectEnd.setText("");
        this.intersectInput.setText("");
        this.intersectOutput.setText("");
    }//GEN-LAST:event_ClearIntersect

    private void InputFileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputFileChooserButtonActionPerformed
        int returnVal = fileSelector.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileSelector.getSelectedFile();
            try {
              // What to do with the file, e.g. display it in a TextArea
                this.intersectInput.setText(file.getAbsolutePath());
            } catch (Exception ex) {
              System.out.println("problem accessing file"+file.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_InputFileChooserButtonActionPerformed

    private void CompFileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompFileChooserButtonActionPerformed
        int returnVal = fileSelector.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileSelector.getSelectedFile();
            try {
              // What to do with the file, e.g. display it in a TextArea
                this.intersectCompFile.setText(file.getAbsolutePath());
            } catch (Exception ex) {
              System.out.println("problem accessing file"+file.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_CompFileChooserButtonActionPerformed

    private void OutputFileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutputFileChooserButtonActionPerformed
        int returnVal = fileSelector.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileSelector.getSelectedFile();
            try {
              // What to do with the file, e.g. display it in a TextArea
                this.intersectOutput.setText(file.getAbsolutePath());
            } catch (Exception ex) {
              System.out.println("problem accessing file"+file.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_OutputFileChooserButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BedFEJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BedFEJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BedFEJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BedFEJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BedFEJFrame().setVisible(true);
            }
        });
    }
    
    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CompFileChooserButton;
    private javax.swing.JButton InputFileChooserButton;
    private javax.swing.JButton OutputFileChooserButton;
    private javax.swing.JFileChooser fileSelector;
    private javax.swing.JTextField intersectChr;
    private javax.swing.JTextField intersectCompFile;
    private javax.swing.JTextField intersectEnd;
    private javax.swing.JTextField intersectInput;
    private javax.swing.JTextField intersectOutput;
    private javax.swing.JTextField intersectStart;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
