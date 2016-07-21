package com.halcyon.calculaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtx;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_0;
    Button btn_clear;
    Button btn_del;
    Button btn_plus;
    Button btn_div;
    Button btn_sub;
    Button btn_multi;
    Button btn_point;
    Button btn_equal;

    boolean isPoint;//是否输入过小数点
    int sumBtnId;//存放最近使用的运算符的ID
    boolean isStart;//是否刚启动或刚清零
    double temp_1;
    double temp_2;
    boolean haveOper;//判断是否已经进行运算,在输入符号后为true,当仅当运算结束后重置为false
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        isPoint=false;
        sumBtnId=0;
        isStart=true;
        temp_1=0d;
        temp_2=0d;
        haveOper=false;

        edtx=(EditText)findViewById(R.id.editText);
        btn_1=(Button)findViewById(R.id.btn_1);
        btn_2=(Button)findViewById(R.id.btn_2);
        btn_3=(Button)findViewById(R.id.btn_3);
        btn_4=(Button)findViewById(R.id.btn_4);
        btn_5=(Button)findViewById(R.id.btn_5);;
        btn_6=(Button)findViewById(R.id.btn_6);
        btn_7=(Button)findViewById(R.id.btn_7);
        btn_8=(Button)findViewById(R.id.btn_8);
        btn_9=(Button)findViewById(R.id.btn_9);
        btn_0=(Button)findViewById(R.id.btn_0);
        btn_point=(Button)findViewById(R.id.btn_point);
        btn_clear=(Button)findViewById(R.id.btn_clear);
        btn_del=(Button)findViewById(R.id.btn_del);
        btn_plus=(Button)findViewById(R.id.btn_plus);
        btn_sub=(Button)findViewById(R.id.btn_sub);
        btn_multi=(Button)findViewById(R.id.btn_multi);
        btn_div=(Button)findViewById(R.id.btn_div);
        btn_equal=(Button)findViewById(R.id.btn_equal);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_multi.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_equal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Button  tempBtn=(Button)v;
        switch (v.getId()) {
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_0:
//                temp_1=enterNum(edtx,tempBtn,temp_1);
//                break;
            case R.id.btn_point:
                enterNum(edtx,tempBtn);
                break;
            //当点击运算符后,如果之前已经输入过运算符，则先计算上一步操作，然后显示结果
            //否则，显示清零
            case R.id.btn_plus:
            case R.id.btn_sub:
            case R.id.btn_multi:
            case R.id.btn_div:
                if(haveOper) {
                    calSum(temp_2, temp_1, sumBtnId);
                    edtx.setText(String.valueOf(temp_2));
                }
                else
                {
                    temp_2=temp_1;
                    clearEdtx();
                }
                sumBtnId=tempBtn.getId();
                haveOper=true;
                break;
            case R.id.btn_del:
                delEdit();
                break;
            case R.id.btn_clear:
                clearEdtx();
                sumBtnId=0;
                temp_2=0d;
                break;
            case R.id.btn_equal:
                if(haveOper)
                    calSum(temp_2,temp_1,sumBtnId);
                else
                    temp_2=temp_1;
                edtx.setText(String.valueOf(temp_2));
               break;
            default:
                break;
        }

    }
  
    //用于对EditEdtx进行删减操作
    private void delEdit( )
    {
        String edtxCon=edtx.getText().toString();
        edtxCon=edtxCon.substring(0,edtxCon.length()-1);
        if(edtxCon.charAt(edtxCon.length()-1)=='.')
			edtxCon=edtxCon.substring(0,edtxCon.length()-1);
        edtx.setText(edtxCon);
        temp_1=Double.valueOf(edtxCon.toString());
    }
    //用于对EditText清零
    private void clearEdtx()
    {
        temp_1 = 0d;
        isPoint=false;
        isStart=true;
        edtx.setText("0");
    }
    //用于输入数字
    private void enterNum(EditText edtx,Button tempBtn)
    {
        //刚启动或清零后
        if(isStart) {
            if(tempBtn.getId()!=R.id.btn_point) {
                edtx.setText(tempBtn.getText());
                temp_1 = Double.valueOf(edtx.getText().toString());
            }
            else
            {
                isPoint = true;
//              edtx.setText(edtx.getText().append("."));//刚启动时，其结果只有一个，不需要使用不安全的表达式
                edtx.setText("0.");
                temp_1=0;
            }
            isStart=false;
        }
        //启动后
        else {
            if(tempBtn.getId()!=R.id.btn_point) {
                edtx.setText(edtx.getText().append(tempBtn.getText()));
                temp_1 = Double.valueOf(edtx.getText().toString());
            }
            else {
                if (isPoint) {
                    clearEdtx();
                    return;
                }
                else {
                    isPoint=true;
                    temp_1=Double.valueOf(edtx.getText().toString());
                    edtx.setText(edtx.getText().append("."));
                }
            }
        }
        return;
    }
    //用于进行运算
    private void calSum(double num_1,double num_2,int sumBtn)
    {
        BigDecimal sum=new BigDecimal(0d);
        BigDecimal tempBD_1=new BigDecimal(num_1);
        BigDecimal tempBD_2=new BigDecimal(num_2);
        switch (sumBtn)
        {
            case R.id.btn_plus:
                sum=tempBD_1.add(tempBD_2);
                break;
            case R.id.btn_sub:
                sum=tempBD_1.subtract(tempBD_2);
                break;
            case R.id.btn_multi:
                sum=tempBD_1.multiply(tempBD_2);
                break;
            case R.id.btn_div:
                if(num_2==0)
                {
                    temp_2=0d;
                    clearEdtx();
                    haveOper=false;
                    return;
                }
                else
                {   //除法保留小数点后3位数字
                    sum=tempBD_1.divide(tempBD_2, 3, RoundingMode.HALF_UP);
                }
                break;
            default:
                break;
        }
        temp_1=sum.doubleValue();
        temp_2=temp_1;
        isPoint=false;
        sumBtnId=0;
        haveOper=false;
        isStart=true;
        return ;
    }
}
