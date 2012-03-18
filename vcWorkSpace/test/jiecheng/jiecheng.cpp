// jiecheng.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "stdafx.h"
#include "stdio.h"
#include "iostream.h"

int main(int argc, char* argv[])
{
int carry,n,j;
    int a[2000];
    int digit=1;
    int temp,i;
    cout<<"please enter n:"<<endl;
    cin>>n;
    a[0]=1;
    for(i=2; i<=n; i++)
    {
        for(carry=0,j=1; j<=digit; ++j)
        {
            temp=a[j-1]*i+carry;
            a[j-1]=temp%10;
            carry=temp/10;
        }
        while(carry)
        {
            //digit++;
            a[++digit-1]=carry%10;
            carry/=10;
        }
    }
    cout<<"the result is:"<<endl;
    for(int k=digit; k>=1; --k)
        cout<<a[k-1];
    cout<<endl;
    return 0;
}

