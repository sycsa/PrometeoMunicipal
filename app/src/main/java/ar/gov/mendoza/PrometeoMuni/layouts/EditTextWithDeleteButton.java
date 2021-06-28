package ar.gov.mendoza.PrometeoMuni.layouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import ar.gov.mendoza.PrometeoMuni.CreateActaActivity;
import ar.gov.mendoza.PrometeoMuni.R;


public class EditTextWithDeleteButton extends LinearLayout{

	private static final String TAG = "EditTextWithDeleteButton";
	
	public HandlerClearTextInterface handlerClearText;
	
	protected EditText editText;
	protected ImageButton clearTextButton;
	
	public void cleanData()
	{
		TextKeyListener.clear( editText.getText());
		idItem=null;
		objectItem=null;
		ocultar();
	}
	/*For Save Object in Control*/
	private Object objectItem;
	public Object getObjectItem() {
		return objectItem;
	}
	public void setObjectItem(Object objectItem) {
		this.objectItem = objectItem;
	}


	
	private Integer idItem;
	public void setText(String sValue)
	{
		editText.setText(sValue);
	}
	
	public void setIdItem(Integer sValue)
	{
	 this.idItem = sValue;
	}

		
	public Integer getIdItem() {
		return idItem;
	}
	public interface TextChangedListener extends TextWatcher{
	}
	
	TextChangedListener editTextListener = null;
	
	public void addTextChangedListener(TextChangedListener listener) {
        this.editTextListener = listener;
    }
	
	public EditTextWithDeleteButton(Context context) {
		super(context);
		//LayoutInflater.from(context).inflate(R.layout.create_acta, this);
	}

	public EditTextWithDeleteButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
		
	}
	
	public EditTextWithDeleteButton(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
		initViews(context, attrs);
	}

	private void ocultar()
	{
		this.setVisibility(View.GONE);
	}

	private void initViews(final Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.EditTextWithDeleteButton, 0, 0);
		String hintText;
		int deleteButtonRes;
		try {
			// get the text and colors specified using the names in attrs.xml
			hintText = a.getString(R.styleable.EditTextWithDeleteButton_hintText);
			deleteButtonRes = a.getResourceId(R.styleable.EditTextWithDeleteButton_deleteButtonRes,R.drawable.text_field_clear_btn);

		} finally {
			a.recycle();
		}
		editText = createEditText(context, hintText);
		clearTextButton = createImageButton(context, deleteButtonRes);

		this.addView(editText);
		this.addView(clearTextButton);
		editText.addTextChangedListener(txtEntered());
	

		editText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus && editText.getText().toString().length() > 0)
					clearTextButton.setVisibility(View.VISIBLE);
				else
					clearTextButton.setVisibility(View.GONE);

			}
		});
		clearTextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (idItem == 158 || idItem.equals(158)) CreateActaActivity.toggleAlcoholemiaVisibility();
				TextKeyListener.clear( editText.getText());
				idItem=null;
				objectItem=null;
			    if (handlerClearText !=null)
			    {
					  handlerClearText.onClearTextItem(v);
			    }
			    ocultar();
			}
		});
	}
	
	public TextWatcher txtEntered() {
		return new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				 if (editTextListener != null)
			            editTextListener.onTextChanged(s, start, before, count);

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (editTextListener != null)
		            editTextListener.afterTextChanged(s);
				if (editText.getText().toString().length() > 0)
					clearTextButton.setVisibility(View.VISIBLE);
				else
					clearTextButton.setVisibility(View.GONE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (editTextListener != null)
		            editTextListener.beforeTextChanged(s, start, count, after);

			}

		};
	}
	
	
	@SuppressLint("NewApi")
	private EditText createEditText(Context context, String hintText) {
		editText = new EditText(context);
		editText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		editText.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 80, 1f));//LayoutParams.WRAP_CONTENT
		editText.setHorizontallyScrolling(false);
		editText.setVerticalScrollBarEnabled(true);
		editText.setGravity(Gravity.LEFT);
		editText.setBackground(null);
		editText.setHint(hintText);
		float valor = editText.getTextSize();
		editText.setTextSize(13.0f);
		//editText.setMinHeight(18);
		editText.setFocusable(false);
		int editT = editText.getMinLines();
		editText.setLines(3);
		editT = editText.getMinHeight();
		editT = editText.getMinimumHeight();
		return editText;
	}

	private ImageButton createImageButton(Context context, int deleteButtonRes) {
		clearTextButton = new ImageButton(context);
		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		params.gravity = Gravity.CENTER_VERTICAL;
		clearTextButton.setLayoutParams(params);
		clearTextButton.setBackgroundResource(deleteButtonRes);
		clearTextButton.setVisibility(View.GONE);
		return clearTextButton;
	}
}
	