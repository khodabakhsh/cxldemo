// WordInputDlg.cpp : implementation file
//

#include "stdafx.h"
#include "WordInput.h"
#include "WordInputDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CWordInputDlg dialog

CWordInputDlg::CWordInputDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CWordInputDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CWordInputDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);

	m_iRightNum = 0; // 初始化为0
	m_iAllNum = 0; // 初始化为0
}

void CWordInputDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CWordInputDlg)
	DDX_Control(pDX, ID_INPUT, m_input);
	DDX_Control(pDX, ID_SHOW, m_show);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CWordInputDlg, CDialog)
	//{{AFX_MSG_MAP(CWordInputDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_EN_CHANGE(ID_INPUT, OnChangeInput)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CWordInputDlg message handlers

BOOL CWordInputDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

 

         m_show.SetReadOnly();//设置只读

         char ch = 'A' + rand()% 26 ;//随机生成一个大写字母

         CString strShow = ch ;

         m_show.SetWindowText(strShow);

		 m_iAllNum++;



	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CWordInputDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CWordInputDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CWordInputDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CWordInputDlg::OnChangeInput() 
{
	// TODO: If this is a RICHEDIT control, the control will not
	// send this notification unless you override the CDialog::OnInitDialog()
	// function and call CRichEditCtrl().SetEventMask()
	// with the ENM_CHANGE flag ORed into the mask.
	
	// TODO: Add your control notification handler code here
	


	   CString strShow ;//显示的内容

         m_show.GetWindowText(strShow);      

         CString strInput ;//用户输入的内容

         m_input.GetWindowText(strInput);
	

        

         strInput.MakeUpper();//因为不区分大小写，所以小写变大写，

         //正确的次数加一

         if(strShow == strInput)

                   m_iRightNum ++ ;

        

         //选中，以便用户下次输入的时候替换掉已有内容

         m_input.SetSel(0,-1);

        

         //随机生成一个大写字母

         char ch = 'A' + rand()% 26 ;

         strShow = ch ;

         m_show.SetWindowText(strShow);

		 m_iAllNum++;
}

void CWordInputDlg::OnOK() 
{
	/**
	统计输入
	*/
   CString strMess ;

         strMess.Format("你共正确输入了%d个字符，字符出现总数为%d",m_iRightNum,m_iAllNum);

         AfxMessageBox(strMess);

 

         CDialog::OnOK();
}
