package com.example.finalapp.ui.home;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalapp.ConnectionReceiver;
import com.example.finalapp.R;
import com.example.finalapp.model.TimeTableDays;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private TimeTableDays timeSlot;
    private SimpleDateFormat format;
    private Calendar calendar;
    private Date start, end, startNew, endNew;
    private int currentHour;
    private int currentMinute;
    private EditText txtSubjectID, txtBatchGroup, txtStartTime, txtEndTime, txtHallNumber, txtLecIns;
    private TimePickerDialog timePickerDialog;
    private Spinner spinnerClassType, spinnerFaculty, spinnerYear, spinnerSemester, spinnerDay;
    private String subjectID = "", batchGroup = "", classType = "", faculty = "", startTime = "", endTime = "", dayWeek = "", hall = "";
    private List<String> lecturer;
    private int yearValue = 0, semesterValue = 0;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private ProgressBar loadingInsert;
    private RelativeLayout loadingLayout;
    private boolean status = true, bool = true, parseStatus = true;
    private HashSet<String> lec;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        Button btnInsert = root.findViewById(R.id.btnInsert);
        Button btnReset = root.findViewById(R.id.btnReset);
        txtSubjectID = root.findViewById(R.id.txtSubjectCode);
        txtBatchGroup = root.findViewById(R.id.txtBatchGroup);
        txtHallNumber = root.findViewById(R.id.txtHallLab);
        txtLecIns = root.findViewById(R.id.txtLecIns);
        txtStartTime = root.findViewById(R.id.txtStartTime);
        txtEndTime = root.findViewById(R.id.txtEndTime);
        spinnerClassType = root.findViewById(R.id.spinnerClassType);
        spinnerFaculty = root.findViewById(R.id.spinnerFaculty);
        spinnerYear = root.findViewById(R.id.spinnerYear);
        spinnerSemester = root.findViewById(R.id.spinnerSemester);
        spinnerDay = root.findViewById(R.id.spinnerDay);
        loadingInsert = root.findViewById(R.id.loadingInsert);
        loadingLayout = root.findViewById(R.id.loadingPanelInsert);


        loadingInsert.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> classTypeSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getApplicationContext()), R.array.classType, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> facultySpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.faculties, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> yearSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.years, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> semesterSpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.semesters, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> daySpinner = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(), R.array.dayOfTheWeek, android.R.layout.simple_spinner_item);

        classTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerClassType.setAdapter(classTypeSpinner);
        spinnerFaculty.setAdapter(facultySpinner);
        spinnerYear.setAdapter(yearSpinner);
        spinnerSemester.setAdapter(semesterSpinner);
        spinnerDay.setAdapter(daySpinner);

        spinnerClassType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    classType = adapterView.getItemAtPosition(i).toString().trim();
                else
                    classType = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                classType = "";
            }
        });

        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    faculty = adapterView.getItemAtPosition(i).toString().trim();
                else
                    faculty = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                faculty = "";
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    yearValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString().trim());
                else
                    yearValue = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                yearValue = 0;
            }
        });

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    semesterValue = Integer.parseInt(adapterView.getItemAtPosition(i).toString().trim());
                else
                    semesterValue = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                semesterValue = 0;
            }
        });

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    dayWeek = adapterView.getItemAtPosition(i).toString().trim();
                else
                    dayWeek = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    dayWeek = "";
            }
        });


        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerForStartTime(view);
            }
        });


        txtStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerForStartTime(view);
                }
            }
        });


        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTimePickerForEndTime(view);
            }
        });


        txtEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerForEndTime(view);
                }
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadingLayout.setVisibility(View.VISIBLE);
                loadingInsert.setVisibility(View.VISIBLE);


                subjectID = txtSubjectID.getText().toString().trim();
                batchGroup = txtBatchGroup.getText().toString().trim().replaceAll("\\s+", "_").replace(".", "_");
                hall = txtHallNumber.getText().toString().trim();
                String[] arr = txtLecIns.getText().toString().trim().split("\n");

                lec = new HashSet<>();

                for (String str : arr) {
                    lec.add(str.trim());
                }

                lecturer = new ArrayList<>();

                lecturer.addAll(lec);

                startTime = txtStartTime.getText().toString().trim();
                endTime = txtEndTime.getText().toString().trim();


                if (subjectID.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (batchGroup.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (classType.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (faculty.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (yearValue == 0) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (semesterValue == 0) {
                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (startTime.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (endTime.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (dayWeek.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (hall.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else if (lecturer.isEmpty()) {


                    loadingInsert.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.requiredField), Toast.LENGTH_SHORT).show();
                } else {


                    if (ConnectionReceiver.getIntConnectionStatus() && ConnectionReceiver.getNetConnectionStatus()) {

                        try {

                            database = FirebaseDatabase.getInstance();

                            dbRef = database.getReference("TimeTable");

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        format = new SimpleDateFormat("HH:mm", Locale.getDefault());

                                        try {


                                            startNew = format.parse(startTime);
                                            endNew = format.parse(endTime);


                                            start = format.parse(Objects.requireNonNull(data.getValue(TimeTableDays.class)).getStarttime());
                                            end = format.parse(Objects.requireNonNull(data.getValue(TimeTableDays.class)).getEndtime());

                                        } catch (ParseException e) {

                                            parseStatus = false;
                                            break;
                                        }


                                        if (startNew.before(endNew)) {
                                            bool = true;

                                            for (String lec : Objects.requireNonNull(data.getValue(TimeTableDays.class)).getLecturer()) {

                                                for (String lecStr : lecturer) {
                                                    if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) && lecStr.equals(lec)) {

                                                        if (endNew.before(start) || endNew.equals(start)) {
                                                            status = true;
                                                            break;

                                                        } else if (startNew.after(end) || startNew.equals(end)) {
                                                            status = true;
                                                            break;

                                                        } else {
                                                            status = false;
                                                            break;
                                                        }


                                                    } else {
                                                        status = true;

                                                    }
                                                }

                                                if (!status)
                                                    break;


                                            }

                                            if (!status)
                                                break;
                                            else {
                                                if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getCombine_id().equals(subjectID + "_" + classType + "_" + batchGroup)) {
                                                    status = false;
                                                    break;
                                                } else if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) &&
                                                        Objects.requireNonNull(data.getValue(TimeTableDays.class)).getGroup().equals(batchGroup)) {

                                                    if (endNew.before(start) || endNew.equals(start)) {
                                                        status = true;
                                                        break;

                                                    } else if (startNew.after(end) || startNew.equals(end)) {
                                                        status = true;
                                                        break;

                                                    } else {
                                                        status = false;
                                                        break;
                                                    }


                                                } else if (Objects.requireNonNull(data.getValue(TimeTableDays.class)).getDay().equals(dayWeek) &&
                                                        Objects.requireNonNull(data.getValue(TimeTableDays.class)).getHallno().equals(hall)) {

                                                    if (endNew.before(start) || endNew.equals(start)) {
                                                        status = true;
                                                        break;

                                                    } else if (startNew.after(end) || startNew.equals(end)) {
                                                        status = true;
                                                        break;

                                                    } else {
                                                        status = false;
                                                        break;
                                                    }

                                                } else {
                                                    status = true;

                                                }

                                            }

                                        } else {
                                            bool = false;
                                            break;
                                        }


                                    }

                                    if (status && bool && parseStatus) {

                                        timeSlot = new TimeTableDays();


                                        timeSlot.setSub_id(subjectID);
                                        timeSlot.setGroup(batchGroup);
                                        timeSlot.setClass_type(classType);
                                        timeSlot.setFaculty(faculty);
                                        timeSlot.setYear(yearValue);
                                        timeSlot.setSem(semesterValue);


                                        timeSlot.setDay(dayWeek);
                                        timeSlot.setHallno(hall);
                                        timeSlot.setLecturer(lecturer);
                                        timeSlot.setCombine_id(timeSlot.getSub_id() + "_" + timeSlot.getClass_type() + "_" + timeSlot.getGroup());

                                        timeSlot.setStarttime(startTime);
                                        timeSlot.setEndtime(endTime);

                                        dbRef.child(timeSlot.getCombine_id()).setValue(timeSlot);


                                        loadingInsert.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);

                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.insertSuccess), Toast.LENGTH_SHORT).show();

                                        clearFields();
                                    } else if (status && parseStatus) {


                                        loadingInsert.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.timeGapError), Toast.LENGTH_SHORT).show();
                                    } else if (status) {
                                        loadingInsert.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);

                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.parseError), Toast.LENGTH_SHORT).show();
                                    } else {


                                        loadingInsert.setVisibility(View.GONE);
                                        loadingLayout.setVisibility(View.GONE);
                                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.timeslotExist), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                    loadingInsert.setVisibility(View.GONE);
                                    loadingLayout.setVisibility(View.GONE);
                                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (DatabaseException e) {


                            loadingInsert.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.databaseError), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        loadingInsert.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();

                    }


                }


            }
        });


        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTimePickerForStartTime(View view) {

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);


        txtStartTime.setShowSoftInputOnFocus(false);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        timePickerDialog = new TimePickerDialog(Objects.requireNonNull(getActivity()).getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {


                txtStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, true);

        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTimePickerForEndTime(View view) {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        txtEndTime.setShowSoftInputOnFocus(false);

        timePickerDialog = new TimePickerDialog(Objects.requireNonNull(getActivity()).getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {


                txtEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourOfDay, minutes));
            }
        }, currentHour, currentMinute, true);

        timePickerDialog.show();
    }


    private void clearFields() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingInsert.setVisibility(View.VISIBLE);
        txtSubjectID.setText("");
        txtBatchGroup.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");
        txtHallNumber.setText("");
        txtLecIns.setText("");
        spinnerClassType.setSelection(0);
        spinnerDay.setSelection(0);
        spinnerFaculty.setSelection(0);
        spinnerSemester.setSelection(0);
        spinnerYear.setSelection(0);
        loadingInsert.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }


}
