package frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import connect.ComUtil;
import main.Main;
import static main.Main.progFont;


public class ConnectFrame extends JFrame implements ActionListener {
	private JLabel text,list,textSt,stat,c;
	private JButton connect,portParam,ok, dis;
	public JComboBox<String> ports;
	public List<Integer> conf;
	

	public ConnectFrame() throws HeadlessException {
		super("Connect");//имя окна то что находится в левом верхнем углу (title) 
				setSize(550, 300);//и так понятно
				setDefaultCloseOperation(3);//та самая фигня про каторую я забывал (действие по нажатию на крестик 3 убить программу)
				text= new JLabel();//текст приветсвия
				setLayout(new GridBagLayout());//установка трассировшика
				// установки JLabel 
				text.setFont(progFont);
				text.setText("Настройка подключения");
				list = new JLabel("COM Порт");//аналогично с text
				list.setFont(progFont);
				
				ports = new JComboBox<>(ComUtil.getPortList());// создание выпадающего списка(инициализация)
				connect = new JButton("Connect");//создание кнопки коннект
				dis = new JButton("Disconnect");
				ok= new JButton("OK");
				
				textSt= new JLabel("Статус :");//ещё одна JLabel
				textSt.setForeground(Color.ORANGE);
				textSt.setFont(progFont);
				
				
				stat = new JLabel();
				stat.setForeground(Color.ORANGE);
				
				dis.setEnabled(false);//делаем кнопку неактивной
				ok.setEnabled(false);
				
				portParam = new JButton("Параметры порта");
				connect.addActionListener(this);//добавляем слушателя
				connect.setActionCommand("Connect");//добавляем команду кнопке
				ports.addActionListener(this);
				ports.setActionCommand("Ports");
				ok.addActionListener(this);
				ok.setActionCommand("Next");
				dis.addActionListener(this);
				dis.setActionCommand("Discon");
				portParam.addActionListener(this);
				portParam.setActionCommand("Param");
				
				
				GridBagConstraints grid = new GridBagConstraints();//это все параметры трассировщика
				grid.gridx=0;
				grid.gridy=0;
				grid.gridwidth=0;
				grid.fill=grid.BOTH;
				grid.anchor=grid.CENTER;
				grid.gridwidth  = GridBagConstraints.REMAINDER; 
				add(text,grid.clone());//добавление панели 
				grid.anchor = GridBagConstraints.NORTHWEST; 
				grid.fill   = GridBagConstraints.NONE;  
				grid.gridheight = 1;
				grid.gridwidth  = 1;
				grid.gridx = GridBagConstraints.RELATIVE; 
				grid.gridy = GridBagConstraints.RELATIVE; 
				grid.insets = new Insets(10, 10, 0, 0);
				add(list,grid);
				add(ports, grid);
				grid.gridwidth  = GridBagConstraints.REMAINDER;
				grid.ipadx = 32;
				add(ok, grid);
				grid.ipadx=0;
				grid.gridwidth=1;
				//add(c = new JLabel((String)ports.getSelectedItem()), grid);
				add(portParam, grid);
				grid.gridx = GridBagConstraints.RELATIVE; 
				grid.gridy = GridBagConstraints.RELATIVE; 
				add(connect, grid);
				grid.gridwidth = GridBagConstraints.REMAINDER;
				add(dis,grid);
				grid.gridwidth=1;
				add(textSt, grid);
				add(stat, grid);
				
				if(Main.conf.contain("ParamsPort")){
					conf=(List<Integer>)Main.conf.getObj("ParamsPort");
				}else {
					JFrame jf = new PortParam(this);
					//this.setVisible(false);
					jf.setVisible(true);
					jf.setResizable(false);
					System.out.println("theur");
				}
				
		
	}
	private boolean connect(String portName){
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ports){
			
		}
			
		
		switch(e.getActionCommand()){
		case "Ports":
			
			break;
		case "Connect":
			if(ComUtil.createPort((String)ports.getSelectedItem())){
				stat.setText("OK");
				stat.setForeground(Color.green);
				ok.setEnabled(true);
				dis.setEnabled(true);
				connect.setEnabled(false);
				portParam.setEnabled(false);
				ports.setEnabled(false);
			}
			else{
				stat.setText("Failed");
				stat.setForeground(Color.red);
			}
			break;
		case "Next":
			this.dispose();
			JFrame mw = new MainWindow();
			mw.setVisible(true);
			Main.mainWindow=(MainWindow) mw;
			break;
		case "Discon":
			ComUtil.closePort();
			ok.setEnabled(false);
			dis.setEnabled(false);
			portParam.setEnabled(true);
			connect.setEnabled(true);
			ports.setEnabled(true);
			break;
		case "Param":
			JFrame jf = new PortParam(this);
			//this.setVisible(false);
			jf.setVisible(true);
			jf.setResizable(false);
			System.out.println("theur");
			break;
		
		
		}
		
	}

	

}
