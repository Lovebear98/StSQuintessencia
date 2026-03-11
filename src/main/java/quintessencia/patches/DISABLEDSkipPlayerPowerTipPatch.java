package quintessencia.patches;


///99% chance this isn't needed, but prepping this juuust in case resolutions smaller than my minimum have issues
///public class SkipPlayerPowerTipPatch {
///    @SpirePatch2(clz = AbstractPlayer.class, method = "renderPowerTips")
///    public static class TipBGone {
///        @SpireInstrumentPatch
///        public static ExprEditor sob() {
///            return new ExprEditor() {
///                @Override
///                public void edit(MethodCall m) throws CannotCompileException {
///                    if (m.getMethodName().equals("isEmpty")){
///                        m.replace("$_ = !quintessencia.ui.AlchemyMenu.AlchemyHovered && $proceed($$);");
///                    }
///                }
///            };
///        }
///    }
///}