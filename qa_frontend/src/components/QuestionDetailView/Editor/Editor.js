import {
    Editor,
    ThemeIcon,
    rootCtx,
    defaultValueCtx,
    editorViewCtx,
    Ctx,
    schemaCtx
} from "@milkdown/core";
import { nord } from "@milkdown/theme-nord";
import { commonmark } from "@milkdown/preset-commonmark";
import { history } from "@milkdown/plugin-history";
import { gfm } from "@milkdown/preset-gfm";
import { ReactEditor, useEditor } from "@milkdown/react";
import {
    tooltip,
    tooltipPlugin,
} from "@milkdown/plugin-tooltip";
import { listener, listenerCtx } from "@milkdown/plugin-listener";
import { prism } from "@milkdown/plugin-prism";
import { menu } from "@milkdown/plugin-menu";
import { block } from "@milkdown/plugin-block";
import { cursor } from "@milkdown/plugin-cursor";
import { clipboard } from "@milkdown/plugin-clipboard";
import { math } from '@milkdown/plugin-math';
import 'katex/dist/katex.min.css';
import { useEffect, useState } from "react";
import { insert, replaceAll } from "@milkdown/utils";
import {Button} from "antd";
import {useLocation, useParams} from "react-router";
import {addAnswer} from "../../../service/QuestionService/QuestionService";
import "../../../css/Editor.css"

export default function Answer(props) {
    const [content, setContent] = useState("请输入你的回答");

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const id=searchParams.get('qid')

    useEffect(() => {
        console.log("content=", content);
    }, [content]);

    function handlePublish(){
        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', id);
        params.append('content', content);
        addAnswer(params);
        props.setAnswer(false);
    }

    const { editor, getInstance } = useEditor(
        (root) =>
            Editor.make()
                .config((ctx) => {
                    ctx.set(rootCtx, root);
                    ctx.set(defaultValueCtx, content);
                    ctx
                        .get(listenerCtx)
                        .beforeMount((ctx) => {
                            console.log("beforeMount");
                        })
                        .mounted((ctx) => {
                            console.log("mounted");
                            insert("# Default Title");
                        })
                        .updated((ctx, doc, prevDoc) => {
                            console.log("updated", doc, prevDoc);
                            console.log("updated JSON", doc.toJSON());
                        })
                        .markdownUpdated((ctx, markdown, prevMarkdown) => {
                            console.log(
                                "markdownUpdated to=",
                                markdown,
                                "\nprev=",
                                prevMarkdown
                            );
                            setContent(markdown);
                        })
                        .blur((ctx) => {
                            console.log("when editor loses focus");
                        })
                        .focus((ctx) => {
                            const view = ctx.get(editorViewCtx);
                            console.log("focus", view);
                        })
                        .destroy((ctx) => {
                            console.log("destroy");
                        });
                })
                .use(tooltip.configure(tooltipPlugin, {
                    top: true,
                }))
                .use(nord)
                .use(commonmark)
                .use(gfm)
                .use(history)
                .use(listener)
                .use(prism)
                .use(menu)
                .use(block)
                .use(cursor)
                .use(clipboard)
                .use(math)
        //.use(slash)
    );

    return (
        <div style={{ backgroundColor: 'white' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <h1 style={{ marginLeft: '2vw' }}>Answer Editors</h1>
                <Button onClick={handlePublish} style={{ marginLeft: 'auto' }}>发布</Button>
            </div>
            <div >
                <ReactEditor editor={editor} />
            </div>
        </div>

    );
}
