// WordInput.h : main header file for the WORDINPUT application
//

#if !defined(AFX_WORDINPUT_H__76A4FAC7_E2FD_4FF1_891F_8A11BD59C584__INCLUDED_)
#define AFX_WORDINPUT_H__76A4FAC7_E2FD_4FF1_891F_8A11BD59C584__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CWordInputApp:
// See WordInput.cpp for the implementation of this class
//

class CWordInputApp : public CWinApp
{
public:
	CWordInputApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CWordInputApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CWordInputApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_WORDINPUT_H__76A4FAC7_E2FD_4FF1_891F_8A11BD59C584__INCLUDED_)
