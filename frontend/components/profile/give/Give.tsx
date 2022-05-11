import { Card } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, Scrollbar, A11y } from "swiper";
import axiosConnector from "utils/axios-connector";
import { useSelector } from "react-redux";
import { RootState } from "store/slice";
import ProductCard from "components/cards/ProductCard";

type Props = {};
interface Member {
  receiverId: number;
  name: string;
  imageString: string;
}

interface GiveResponse {
  nonMemberList: Member[];
  memberList: Member[];
}

const Give = (props: Props) => {
  return <div>testesssdasdtt</div>;
};

export default Give;
