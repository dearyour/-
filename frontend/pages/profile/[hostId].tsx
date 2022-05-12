import { style } from "@mui/system";
import styles from "styles/profile/profilePage.module.css";
import { useEffect, useState } from "react";
import ProfileInfo from "components/profile/ProfileInfo";
import ProfileMain from "components/profile/main/ProfileMain";
import Wish from "components/profile/wish/Wish";
import Give from "components/profile/give/Give";
import Lantern from "components/profile/lantern/Lantern";
import Chat from "components/profile/chat/Chat";
import Setting from "components/profile/setting/Setting";
import { useRouter } from "next/router";
import { useDispatch, useSelector } from "react-redux";
import { profileActions } from "store/slice/profile";
import { RootState } from "store/slice";

type Props = {};

const Profile = (props: Props) => {
  const router = useRouter();
  const dispatch = useDispatch();
  const { hostId } = router.query;
  const profile = useSelector((state: RootState) => state.profile.profile);

  useEffect(() => {
    console.log("router.isReady", router.isReady);
    if (!router.isReady) {
      return;
    }
    console.log("hostId", hostId);
    dispatch(profileActions.setHostId(hostId));
    dispatch(profileActions.getProfile(hostId));
  }, [router.isReady]);

  // useEffect(() => {
  //   return function cleanup() {
  //     dispatch(profileActions.reset());
  //     console.log("cleanup");
  //   };
  // });

  const sideBtnNames = [
    "호패",
    "연등회 모음",
    "갖고 싶소",
    "주고 싶소",
    "추천 내역",
    "설정",
  ];
  const [selectedBtn, setSelectedBtn] = useState<string>("호패");

  const sideBtns = () => {
    const sideBtnNamesLen = sideBtnNames.length;
    const result = new Array(sideBtnNamesLen).fill(null);
    for (let i = 0; i < sideBtnNamesLen; i++) {
      const sideBtnName = sideBtnNames[i];
      result[i] = (
        <div
          className={
            (selectedBtn === sideBtnName
              ? styles.selected_btn
              : styles.side_btn) + " center_flex bold clickable"
          }
          onClick={(e) => {
            setSelectedBtn(sideBtnName);
            dispatch(profileActions.reset());
          }}
        >
          {sideBtnName}
        </div>
      );
    }
    return result;
  };

  return (
    <div className="center">
      {profile ? (
        <div className={styles.modal}>
          <div className="d-flex justify-content-between">
            {/* 본문 영역 */}
            <div className={styles.main_content + " shadow d-flex"}>
              {/* 프로필 정보 */}
              {selectedBtn === "호패" ? (
                <div
                  className={
                    styles.profile_info + " d-flex justify-content-center"
                  }
                >
                  <ProfileInfo />
                </div>
              ) : selectedBtn === "연등회 모음" ? (
                <div
                  className={
                    styles.profile_info + " d-flex justify-content-center"
                  }
                >
                  <ProfileInfo />
                </div>
              ) : selectedBtn === "주고 싶소" ? null : selectedBtn ===
                "갖고 싶소" ? null : selectedBtn ===
                "추천 내역" ? null : selectedBtn === "설정" ? null : null}
              {/* 콘텐츠 영역 */}
              <div className={styles.content + " center_flex"}>
                {selectedBtn === "호패" ? (
                  <ProfileMain />
                ) : selectedBtn === "연등회 모음" ? (
                  <Lantern />
                ) : selectedBtn === "주고 싶소" ? (
                  <Give />
                ) : selectedBtn === "갖고 싶소" ? (
                  <Wish props={hostId} />
                ) : selectedBtn === "추천 내역" ? (
                  <Chat />
                ) : selectedBtn === "설정" ? (
                  <Setting />
                ) : null}
              </div>
            </div>
            {/* 버튼 영역 */}
            <div className={styles.side_btns + " font_3"}>{sideBtns()}</div>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default Profile;
