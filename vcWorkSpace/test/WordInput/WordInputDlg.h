// WordInputDlg.h : header file
//

#if !defined(AFX_WORDINPUTDLG_H__41848359_7619_4638_A8FB_718A30849B78__INCLUDED_)
#define AFX_WORDINPUTDLG_H__41848359_7619_4638_A8FB_718A30849B78__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CWordInputDlg dialog

class CWordInputDlg : public CDialog
{
// Construction
public:
	CWordInputDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CWordInputDlg)
	enum { IDD = IDD_WORDINPUT_DIALOG };
	CEdit	m_input;
	CEdit	m_show;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CWordInputDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	int m_iRightNum;  //输入正确数统计
		int m_iAllNum;  //总数统计

	// Generated message map functions
	//{{AFX_MSG(CWordInputDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnChangeInput();
	virtual void OnOK();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_WORDINPUTDLG_H__41848359_7619_4638_A8FB_718A30849B78__INCLUDED_)
