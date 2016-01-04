/**
 * Autogenerated code by SdkModelGenerator.
 * Do not edit. Any modification on this file will be removed automatically after project build
 *
 */
package com.hotelbeds.distribution.hotel_api_model.auto.annotation.validators;

/*
 * #%L
 * Hotel API SDK Model
 * %%
 * Copyright (C) 2015 - 2016 HOTELBEDS TECHNOLOGY, S.L.U.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hotelbeds.distribution.hotel_api_model.auto.messages.BookingListRQ;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidBookingListPaginationValidator implements ConstraintValidator<ValidBookingListPagination, Object> {
    private long maxBookingsRange;

    @Override
    public void initialize(ValidBookingListPagination constraintAnnotation) {
        maxBookingsRange = constraintAnnotation.maxBookingsRange();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!(value instanceof BookingListRQ)) {
            throw new IllegalArgumentException("Expected a parameter of type XMLBookingListRQ or JSONBookingListRQ");
        }
        Integer from = null;
        Integer to = null;
        if (value instanceof BookingListRQ) {
            from = ((BookingListRQ) value).getFrom();
            to = ((BookingListRQ) value).getTo();
        }
        context.disableDefaultConstraintViolation();
        boolean result = true;
        if (from != null && from <= 0) {
            context.buildConstraintViolationWithTemplate(
                "{com.hotelbeds.distribution.hotelapi.engine.messages.BookingListRQ.pagination.minimun.message}")
                .addConstraintViolation();
            log.info("The minimun value for the parameter From is 1, from: " + from);
            result = false;
        } else if (from != null && to != null && from.compareTo(to) > 0) {
            context.buildConstraintViolationWithTemplate(
                "{com.hotelbeds.distribution.hotelapi.engine.messages.BookingListRQ.pagination.before.message}")
                .addConstraintViolation();
            log.info("The parameter To must be greater than From, from: " + from + " , to: " + to);
            result = false;
        } else if (from != null && to != null && !isValidBookingsRange(from, to)) {
            context.buildConstraintViolationWithTemplate(
                "{com.hotelbeds.distribution.hotelapi.engine.messages.BookingListRQ.pagination.range.message}")
                .addConstraintViolation();
            log.info("The number of bookings between To and From parameters must be less than or equal to " + maxBookingsRange + ", from: " + from
                + " , to: " + to);
            result = false;
        }
        return result;
    }

    private boolean isValidBookingsRange(Integer from, Integer to) {
        // from and to are inclusive
        final long bookings = to - from + 1L;
        boolean result = true;
        if (bookings > maxBookingsRange) {
            result = false;
        }
        return result;
    }
}
