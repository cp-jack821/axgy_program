package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.AppointmentStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringToAppointmentStatusConverter implements Converter<String, AppointmentStatus> {
    @Override
    public AppointmentStatus convert(String source) {
        return Arrays.stream(AppointmentStatus.values()).filter(appointmentStatus -> source.equals(appointmentStatus.getCode()))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
