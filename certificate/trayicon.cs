using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;

namespace WindowsFormsApp2
{

    public partial class Form1 : Form
    {
        private System.Windows.Forms.ContextMenu contextMenu1;
        private System.Windows.Forms.MenuItem menuItem1;
        private System.Windows.Forms.MenuItem menuItem2;
        private int port = 5002;

        public Form1()
        {
            InitializeComponent();

            this.contextMenu1 = new System.Windows.Forms.ContextMenu();
            this.menuItem1 = new System.Windows.Forms.MenuItem();
            this.menuItem2 = new System.Windows.Forms.MenuItem();

            this.contextMenu1.MenuItems.AddRange(
                    new System.Windows.Forms.MenuItem[] { this.menuItem1, this.menuItem2 });

            // Initialize menuItem1
            this.menuItem1.Index = 0;
            this.menuItem1.Text = "E&xit";
            this.menuItem1.Click += new System.EventHandler(this.menuItem1_Close);

            this.menuItem2.Index = 1;
            this.menuItem2.Text = "Request";
            this.menuItem2.Click += new System.EventHandler(this.menuItem2_Request);

            // Set up how the form should be displayed.
            this.ClientSize = new System.Drawing.Size(292, 266);
            this.Text = "Notify Icon Example";
            // The ContextMenu property sets the menu that will
            // appear when the systray icon is right clicked.
            this.notifyIcon1.ContextMenu = this.contextMenu1;
            notifyIcon1.Text = "Form1 (NotifyIcon example)";

            this.WindowState = FormWindowState.Minimized;
            this.ShowInTaskbar = false;
            this.Visible = false;
            this.notifyIcon1.Visible = true;

            this.notifyIcon1.ContextMenuStrip = contextMenuStrip1;
            notifyIcon1.DoubleClick += new System.EventHandler(this.notifyIcon1_DoubleClick);
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Application.Exit();
        }

        private void notifyIcon1_DoubleClick(object Sender, EventArgs e)
        {
            // Show the form when the user double clicks on the notify icon.

            // Set the WindowState to normal if the form is minimized.
            if (this.WindowState == FormWindowState.Minimized)
                this.WindowState = FormWindowState.Normal;

            // Activate the form.
            this.Activate();
        }
        private void menuItem1_Close(object Sender, EventArgs e)
        {
            // Close the form, which closes the application.
            this.Close();
        }

        private void menuItem2_Request(object Sender, EventArgs e)
        {
            String path = "D:\\temp\\MyText.txt";
            String readText = null;
            if (File.Exists(path))
            {
                readText = File.ReadAllText(path);
                this.notifyIcon1.BalloonTipText = readText;
                notifyIcon1.BalloonTipTitle = "Info!";
                notifyIcon1.ShowBalloonTip(500);
            }

        }
    }

}
